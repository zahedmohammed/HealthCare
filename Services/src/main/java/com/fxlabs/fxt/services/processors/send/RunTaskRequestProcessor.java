package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.converters.project.AuthConverter;
import com.fxlabs.fxt.converters.project.PoliciesConverter;
import com.fxlabs.fxt.dao.entity.project.Auth;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.EnvironmentRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.dto.project.HttpMethod;
import com.fxlabs.fxt.dto.project.TestCase;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.RunConstants;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.clusters.ClusterService;
import com.fxlabs.fxt.services.util.DataResolver;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Component
@Transactional
public class RunTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private AmqpClientService botClientService;
    private TestSuiteRepository testSuiteRepository;
    private RunRepository runRepository;
    private PoliciesConverter policiesConverter;
    private TestSuiteESRepository testSuiteESRepository;
    private EnvironmentRepository environmentRepository;
    private ClusterService clusterService;
    private DataResolver dataResolver;
    private AuthConverter authConverter;

    public RunTaskRequestProcessor(AmqpClientService botClientService, TestSuiteRepository testSuiteRepository,
                                   RunRepository runRepository, PoliciesConverter policiesConverter,
                                   TestSuiteESRepository testSuiteESRepository, EnvironmentRepository environmentRepository,
                                   ClusterService clusterService, DataResolver dataResolver, AuthConverter authConverter) {
        this.botClientService = botClientService;
        this.testSuiteRepository = testSuiteRepository;
        this.runRepository = runRepository;
        this.policiesConverter = policiesConverter;
        this.testSuiteESRepository = testSuiteESRepository;
        this.environmentRepository = environmentRepository;
        this.clusterService = clusterService;
        this.dataResolver = dataResolver;
        this.authConverter = authConverter;
    }

    public void process() {

        //logger.info("started...");
        Stream<Run> runs = runRepository.findByStatus(TaskStatus.WAITING);

        runs.forEach(run -> {

            try {
                Environment env = validateEnvironment(run);
                if (env == null) {
                    return;
                }

                final String region = validateRegion(run);
                if (region == null) {
                    return;
                }

                final List<String> suites;
                String _suite = run.getAttributes().get(RunConstants.SUITES);
                if (!StringUtils.isEmpty(_suite)) {
                    String[] tokens = org.apache.commons.lang3.StringUtils.split(_suite, ",");
                    suites = Arrays.asList(tokens);
                } else {
                    suites = null;
                }

                final List<String> tags;
                String _tags = run.getAttributes().get(RunConstants.TAGS);
                if (!StringUtils.isEmpty(_tags)) {
                    String[] tokens = org.apache.commons.lang3.StringUtils.split(_tags, ",");
                    tags = Arrays.asList(tokens);
                } else {
                    tags = run.getJob().getTags();
                }

                logger.info("Sending task to region [{}]...", region);

                // TODO - Filter Suites by Overridden-suites, Overridden-Tags, tags.
                Stream<TestSuite> list;
                if (suites != null) {
                    list = testSuiteESRepository.findByProjectIdAndNameIn(run.getJob().getProject().getId(), suites);
                } else if (!CollectionUtils.isEmpty(tags)) {
                    list = testSuiteESRepository.findByProjectIdAndTypeAndTagsIn(run.getJob().getProject().getId(), TestSuiteType.SUITE.toString(), tags);
                } else {
                    list = testSuiteESRepository.findByProjectIdAndType(run.getJob().getProject().getId(), TestSuiteType.SUITE.toString());
                }

                boolean generateTestCaseResponse = !StringUtils.isEmpty(run.getJob().getIssueTracker());
                String _projectId = run.getJob().getProject().getId();
                String _project = run.getJob().getProject().getName();
                String _job = run.getJob().getName();
                String _jobId = run.getJob().getId();
                String _env = run.getJob().getEnvironment();
                String _region = run.getJob().getRegions();

                run.getTask().setStatus(TaskStatus.PROCESSING);
                runRepository.saveAndFlush(run);

                AtomicLong total = new AtomicLong(0);

                list.parallel().forEach(testSuite -> {
                    //logger.info("Request {}", i.incrementAndGet());

                    try {

                        if (BooleanUtils.isTrue(testSuite.isInactive())) {
                            return;
                        }

                        // TODO - Replace with the query
                        //if (suites != null && !CollectionUtils.contains(suites.iterator(), testSuite.getName())) {
                        //    return;
                        //}

                        BotTask task = new BotTask();
                        task.setId(run.getId());
                        task.setProject(_project);
                        task.setProjectId(_projectId);
                        task.setJob(_job);
                        task.setJobId(_jobId);
                        task.setEnv(_env);
                        task.setRegion(_region);
                        task.setSuiteName(testSuite.getName());
                        task.setProjectDataSetId(testSuite.getId());
                        task.setGenerateTestCaseResponse(generateTestCaseResponse);

                        task.setPolicies(policiesConverter.convertToDto(testSuite.getPolicies()));

                        task.setMethod(convert(testSuite.getMethod()));

                        copyHeaders(task, testSuite);

                        copyRequests(task, testSuite);

                        copyAuth(run, env, task, testSuite);

                        copyAssertions(task, testSuite);

                        task.setEndpoint(getBaseUrl(env.getBaseUrl()) + testSuite.getEndpoint());

                        // init & cleanup copy
                        copy(testSuite.getInit(), task.getInit(), run, env);
                        copy(testSuite.getCleanup(), task.getCleanup(), run, env);

                        // count tests
                        if (testSuite.getTestCases() != null) {
                            total.getAndAdd(testSuite.getTestCases().size());
                        }

                        // repeat value
                        if (task.getPolicies() != null && task.getPolicies().getRepeat() != null) {
                            total.getAndAdd(task.getPolicies().getRepeat() - 1);
                        }

                        botClientService.sendTask(task, region);
                    } catch (Exception ex) {
                        logger.warn(ex.getLocalizedMessage(), ex);
                    }
                });

                run.getTask().setTotalTests(total.get());
                runRepository.saveAndFlush(run);
            } catch (RuntimeException ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }

        });
    }

    private void copyAssertions(BotTask task, TestSuite ds) {
        // TODO - JPA lazy-load work-around
        List<String> assertions = new ArrayList<>();
        for (String assertion : ds.getAssertions()) {
            assertions.add(assertion);
        }
        task.setAssertions(assertions);
    }

    private void copyHeaders(BotTask task, TestSuite ds) {
        // TODO - JPA lazy-load work-around
        List<String> headers = new ArrayList<>();
        if (ds.getHeaders() != null)
            for (String header : ds.getHeaders()) {
                headers.add(header);
            }
        task.setHeaders(headers);
    }

    private void copyRequests(BotTask task, TestSuite ds) {
        // TODO - JPA lazy-load work-around
        List<TestCase> testCases = new ArrayList<>();
        if (ds.getTestCases() != null)
            for (com.fxlabs.fxt.dao.entity.project.TestCase testCase : ds.getTestCases()) {
                if (!BooleanUtils.isTrue(testCase.getInactive())) {
                    TestCase tc = new TestCase();
                    tc.setId(testCase.getId());
                    tc.setBody(testCase.getBody());
                    testCases.add(tc);
                }
            }
        task.setTestCases(testCases);
    }

    private void copyAuth(Run run, Environment env, BotTask task, TestSuite ds) {
        // if empty resolves it to Default
        // if NONE resolves it to none.
        // if value then finds and injects
        List<Auth> creds = env.getAuths();
        if (StringUtils.isEmpty(ds.getAuth())) {
            for (Auth cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), "default")) {
                    copyCred(task, cred);
                }
            }
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(ds.getAuth(), "none")) {
            // don't send auth
        } else {
            for (Auth cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), ds.getAuth())) {
                    copyCred(task, cred);
                }
            }
        }
    }

    private void copyCred(BotTask task, Auth cred) {

        task.setAuth(authConverter.convertToDto(cred));

        //task.setAuthType(cred.getAuthType());
        task.getAuth().setUsername(dataResolver.resolve(cred.getUsername()));
        task.getAuth().setPassword(dataResolver.resolve(cred.getPassword()));

        task.getAuth().setClientId(dataResolver.resolve(cred.getClientId()));
        task.getAuth().setClientSecret(dataResolver.resolve(cred.getClientSecret()));
    }

    private String getBaseUrl(String url) {
        return dataResolver.resolve(url);
    }

    private HttpMethod convert(com.fxlabs.fxt.dao.entity.project.HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET:
                return HttpMethod.GET;
            case DELETE:
                return HttpMethod.DELETE;
            case HEAD:
                return HttpMethod.HEAD;
            case PUT:
                return HttpMethod.PUT;
            case POST:
                return HttpMethod.POST;
            case OPTIONS:
                return HttpMethod.OPTIONS;
            case PATCH:
                return HttpMethod.PATCH;
            case TRACE:
                return HttpMethod.TRACE;
        }
        return null;
    }


    private void copy(List<String> after, List<BotTask> tasks, Run run, Environment env) {
        if (CollectionUtils.isEmpty(after) || after.isEmpty()) {
            return;
        }

        for (String suite : after) {
            logger.info("Processing after suite [{}]", suite);

            TestSuite suite1 = testSuiteRepository.findByProjectIdAndTypeAndName(run.getJob().getProject().getId(), TestSuiteType.ABSTRACT, suite);

            if (suite1 == null) {
                logger.warn("No suite found for project [{}] with suite-name [{}]", run.getJob().getProject().getId(), suite);
                continue;
            }

            logger.info("Suite id [{}]", suite1.getId());

            BotTask afterTask = new BotTask();
            afterTask.setSuiteName(suite1.getName());
            afterTask.setMethod(convert(suite1.getMethod()));

            copyHeaders(afterTask, suite1);

            copyRequests(afterTask, suite1);

            copyAuth(run, env, afterTask, suite1);

            copyAssertions(afterTask, suite1);

            afterTask.setEndpoint(getBaseUrl(env.getBaseUrl()) + suite1.getEndpoint());

            tasks.add(afterTask);
        }
    }

    private Environment findEvn(String projectId, String env) {

        List<Environment> envs = environmentRepository.findByProjectId(projectId);
        Environment env_ = null;
        for (Environment environment : envs) {
            if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(env, environment.getName())) {
                env_ = environment;
                break;
            }
        }

        return env_;
    }

    private Environment validateEnvironment(Run run) {
        String envName;
        if (run.getAttributes().containsKey(RunConstants.ENV)) {
            envName = run.getAttributes().get(RunConstants.ENV);
        } else {
            envName = run.getJob().getEnvironment();
        }

        Environment env = findEvn(run.getJob().getProject().getId(), envName);
        // TODO - Fail if not a valid env.
        if (env == null) {
            //run.getTask().setStatus(TaskStatus.FAIL);
            //run.getTask().setDescription(String.format("Invalid Env: %s", envName));
            //runRepository.saveAndFlush(run);
            return null;
        }
        return env;
    }

    private String validateRegion(Run run) {
        final String region;
        // check for overriding
        if (run.getAttributes().containsKey(RunConstants.REGION)) {
            region = run.getAttributes().get(RunConstants.REGION);
        } else {
            region = run.getJob().getRegions();
        }

        // TODO - Fail if not a valid region
        if (org.apache.commons.lang3.StringUtils.isEmpty(region)) {
            run.getTask().setStatus(TaskStatus.FAIL);
            run.getTask().setDescription(String.format("Invalid Region: %s", region));
            runRepository.saveAndFlush(run);
            return null;
        }

        // get cluster by name
        Response<Cluster> clusterResponse = clusterService.findByName(region, run.getJob().getCreatedBy());
        if (clusterResponse.isErrors()) {
            run.getTask().setStatus(TaskStatus.FAIL);
            run.getTask().setDescription(String.format("Invalid Region: %s", region));
            runRepository.saveAndFlush(run);
            return null;
        }

        return clusterResponse.getData().getKey();
    }

    private boolean isValidSuiteCount(Stream<TestSuite> list, Run run) {
        if (list.count() <= 0) {
            run.getTask().setStatus(TaskStatus.FAIL);
            run.getTask().setDescription(String.format("No suites to run."));
            runRepository.saveAndFlush(run);
            return false;
        }
        return true;
    }

}
