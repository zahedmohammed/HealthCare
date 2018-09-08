package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.converters.project.AuthConverter;
import com.fxlabs.fxt.converters.project.PoliciesConverter;
import com.fxlabs.fxt.dao.entity.project.*;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.es.TestSuiteESRepository;
import com.fxlabs.fxt.dao.repository.jpa.EnvironmentRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dao.repository.jpa.ository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.dto.events.Entity;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.dto.events.Status;
import com.fxlabs.fxt.dto.events.Type;
import com.fxlabs.fxt.dto.project.HttpMethod;
import com.fxlabs.fxt.dto.project.TestCase;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.LightWeightBotTask;
import com.fxlabs.fxt.dto.run.RunConstants;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.dto.skills.Opt;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.clusters.ClusterService;
import com.fxlabs.fxt.services.events.LocalEventPublisher;
import com.fxlabs.fxt.services.exceptions.FxException;
import com.fxlabs.fxt.services.processors.receiver.RunTaskResponseProcessor;
import com.fxlabs.fxt.services.util.DataResolver;
import org.apache.commons.lang3.BooleanUtils;
import org.jasypt.util.text.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Component
@Transactional
public class InstantRunTaskRequestProcessor {

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
    private TextEncryptor encryptor;
    private ProjectRepository projectRepository;
    private LocalEventPublisher localEventPublisher;
    private RunTaskResponseProcessor runTaskResponseProcessor;

    public InstantRunTaskRequestProcessor(AmqpClientService botClientService, TestSuiteRepository testSuiteRepository,
                                          RunRepository runRepository, PoliciesConverter policiesConverter, LocalEventPublisher localEventPublisher,
                                          TestSuiteESRepository testSuiteESRepository, EnvironmentRepository environmentRepository,
                                          ClusterService clusterService, DataResolver dataResolver, AuthConverter authConverter,
                                          TextEncryptor encryptor, ProjectRepository projectRepository, RunTaskResponseProcessor runTaskResponseProcessor) {
        this.botClientService = botClientService;
        this.testSuiteRepository = testSuiteRepository;
        this.runRepository = runRepository;
        this.policiesConverter = policiesConverter;
        this.testSuiteESRepository = testSuiteESRepository;
        this.environmentRepository = environmentRepository;
        this.clusterService = clusterService;
        this.dataResolver = dataResolver;
        this.authConverter = authConverter;
        this.encryptor = encryptor;
        this.projectRepository = projectRepository;
        this.localEventPublisher = localEventPublisher;
        this.runTaskResponseProcessor = runTaskResponseProcessor;
    }

    public List<com.fxlabs.fxt.dto.run.TestSuiteResponse> process(String region, Environment env, TestSuite testSuite) {

         List<com.fxlabs.fxt.dto.run.TestSuiteResponse> testSuiteResponses = null;
            try {


                if (env == null) {
                    throw new FxException("Invalid environment");
                }


                if (region == null) {
                    throw new FxException("Invalid region");
                }

                final String region_ = validateRegion(region, testSuite.getCreatedBy());
                if (region == null) {
                    throw new FxException("Invalid region");
                }

                logger.info("Sending Tessuite [{}] to region [{}]...", testSuite.getName(), region);


               // boolean generateTestCaseResponse = isGenerateTestCaseResponse(run);
                String _projectId = testSuite.getProject().getId();
                String _project = testSuite.getProject().getName();
                String _env = env.getName();
                String _region = region;

                String orgName_ = null;
                Optional<Project> projectOptional = projectRepository.findById(_projectId);
                if (projectOptional.isPresent()) {
                    orgName_ = projectOptional.get().getOrg().getName();
                }
                final String orgName = orgName_;

                AtomicLong total = new AtomicLong(0);


                    //logger.info("Request {}", i.incrementAndGet());

                    try {

                        if (BooleanUtils.isTrue(testSuite.isInactive())) {
                            return null;
                        }

                        // TODO - Replace with the query
                        //if (suites != null && !CollectionUtils.contains(suites.iterator(), testSuite.getName())) {
                        //    return;
                        //}

                        BotTask task = new BotTask();
                        task.setProject(_project);
                        task.setProjectId(_projectId);
                        task.setEnv(_env);
                        task.setRegion(_region);
                        task.setSuiteName(testSuite.getName());
                        task.setProjectDataSetId(testSuite.getId());
                        task.setGenerateTestCaseResponse(true);

                        task.setPolicies(policiesConverter.convertToDto(testSuite.getPolicies()));

                        task.setMethod(convert(testSuite.getMethod()));

                        if (testSuite.getCategory() != null) {
                            task.setCategory(testSuite.getCategory().toString());
                        }
                        if (testSuite.getSeverity() != null) {
                            task.setSeverity(testSuite.getSeverity().toString());
                        }
                        copyHeaders(task, testSuite);

                        copyRequests(task, testSuite);

                        copyAuth(env, task, testSuite, orgName);

                        copyAssertions(task, testSuite);

                        task.setEndpoint(getBaseUrl(env.getBaseUrl(), testSuite.getProject().getOrg().getName()) + testSuite.getEndpoint());

                        // init & init.cleanup copy
                        copy(testSuite.getInit(), task.getInit(), env, true, orgName, testSuite);
                        // cleanup copy
                        copy(testSuite.getCleanup(), task.getCleanup(), env, false, orgName, testSuite);

                        // count tests
                        if (testSuite.getTestCases() != null && !testSuite.getTestCases().isEmpty()) {
                            total.getAndAdd(testSuite.getTestCases().size());
                        } else {
                            total.getAndIncrement();
                        }

                        // repeat value
                        if (task.getPolicies() != null && task.getPolicies().getRepeat() != null) {
                            total.getAndAdd(task.getPolicies().getRepeat() - 1);
                        }
                        LightWeightBotTask lightWeightBotTask = new LightWeightBotTask();
                        lightWeightBotTask.setBotTask(task);
                        List<BotTask> response = botClientService.sendTask(lightWeightBotTask, region_);
                        testSuiteResponses = runTaskResponseProcessor.processTaskResponse(response);
                    } catch (Exception ex) {
                        logger.warn(ex.getLocalizedMessage(), ex);
                    }

                return testSuiteResponses;
            } catch (Exception ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }

            return testSuiteResponses;
    }

    private boolean isGenerateTestCaseResponse(Run run) {
        if (run.getJob().getIssueTracker() == null) {
            return false;
        }
        return !StringUtils.isEmpty(run.getJob().getIssueTracker().getName());
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

    private void copyAuth(Environment env, BotTask task, TestSuite ds, String orgName) {
        // if empty resolves it to Default
        // if NONE resolves it to none.
        // if value then finds and injects
        List<Auth> creds = env.getAuths();
        if (StringUtils.isEmpty(ds.getAuth())) {
            for (Auth cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), "default")) {
                    copyCred(task, cred, orgName);
                }
            }
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(ds.getAuth(), "none") || org.apache.commons.lang3.StringUtils.equalsIgnoreCase(ds.getAuth(), "anonymous")) {
            // don't send auth
        } else {
            for (Auth cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), ds.getAuth())) {
                    copyCred(task, cred, orgName);
                }
            }
        }
    }

    private void copyCred(BotTask task, Auth cred, String orgName) {

        if (cred != null && cred.getAuthType() != null && cred.getAuthType() == AuthType.No_Authentication) {
            return;
        }

        task.setAuth(authConverter.convertToDto(cred));

        //task.setAuthType(cred.getAuthType());
        task.getAuth().setUsername(dataResolver.resolve(cred.getUsername(), orgName));
        task.getAuth().setPassword(dataResolver.resolve(cred.getPassword(), orgName));

        task.getAuth().setClientId(dataResolver.resolve(cred.getClientId(), orgName));
        task.getAuth().setClientSecret(dataResolver.resolve(cred.getClientSecret(), orgName));

        // Token
        task.getAuth().setHeader_1(dataResolver.resolve(cred.getHeader_1(), orgName));
        task.getAuth().setHeader_2(dataResolver.resolve(cred.getHeader_2(), orgName));
        task.getAuth().setHeader_3(dataResolver.resolve(cred.getHeader_3(), orgName));

    }

    private String getBaseUrl(String url, String orgName) {
        return dataResolver.resolve(url, orgName);
    }

    private HttpMethod convert(com.fxlabs.fxt.dao.entity.project.HttpMethod httpMethod) {
        if (httpMethod == null) return  null;

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


    private void copy(List<String> after, List<BotTask> tasks, Environment env, boolean copyCleanup, String orgName, TestSuite testSuite_) {
        if (CollectionUtils.isEmpty(after) || after.isEmpty()) {
            return;
        }

        for (String suite : after) {
            logger.debug("Processing after suite [{}]", suite);

            TestSuite suite1 = testSuiteRepository.findByProjectIdAndTypeAndName(testSuite_.getProject().getId(), TestSuiteType.ABSTRACT, suite);

            if (suite1 == null) {
                logger.warn("No suite found for project [{}] with suite-name [{}]", testSuite_.getProject().getId(), suite);
                continue;
            }

            logger.debug("Suite id [{}]", suite1.getId());

            BotTask afterTask = new BotTask();
            afterTask.setSuiteName(suite1.getName());
            afterTask.setMethod(convert(suite1.getMethod()));

            if (suite1.getCategory() != null) {
                afterTask.setCategory(suite1.getCategory().toString());
            }

            if (suite1.getSeverity() != null) {
                afterTask.setSeverity(suite1.getSeverity().toString());
            }

            copyHeaders(afterTask, suite1);

            copyRequests(afterTask, suite1);

            copyAuth(env, afterTask, suite1, orgName);

            copyAssertions(afterTask, suite1);

            afterTask.setEndpoint(getBaseUrl(env.getBaseUrl(), testSuite_.getProject().getOrg().getName()) + suite1.getEndpoint());

            tasks.add(afterTask);

            if (copyCleanup) {
                copy(suite1.getCleanup(), afterTask.getCleanup(), env, false, orgName, suite1);
            }
        }
    }

    private Environment findEvn(String projectId, String env) {

        List<Environment> envs = environmentRepository.findAllByProjectIdAndInactive(projectId, false);
        Environment env_ = null;
        for (Environment environment : envs) {
            if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(env, environment.getName())) {
                env_ = environment;
                break;
            }
        }

        return env_;
    }

    private Environment validateEnvironment(Run run, TestSuite dto) {
        String envName;
        if (run.getAttributes().containsKey(RunConstants.ENV)) {
            envName = run.getAttributes().get(RunConstants.ENV);
        } else {
           throw new FxException("Env missing");
        }

        Environment env = null;
        Optional<Environment> environmentOptional = environmentRepository.findByProjectIdAndNameAndInactive(dto.getProject().getId(), envName, false);

        if (environmentOptional.isPresent()){
            env = environmentOptional.get();
        }

       // Environment env = run.getJob().getEnvironment();
        // TODO - Fail if not a valid env.
        if (env == null) {
            //run.getTask().setStatus(TaskStatus.FAIL);
            //run.getTask().setDescription(String.format("Invalid Env: %s", envName));
            //runRepository.saveAndFlush(run);
            return null;
        }
        return env;
    }

    private String validateRegion(String region, String user) {

        // TODO - Fail if not a valid region
        if (org.apache.commons.lang3.StringUtils.isEmpty(region)) {
            return null;
        }

        // get cluster by name
        Response<Cluster> clusterResponse = clusterService.findByName(region, user);
        if (clusterResponse.isErrors()) {
            return null;
        }

        String key = clusterResponse.getData().getKey();
        return encryptor.decrypt(key);
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


    private void projectSyncEvent(Job job, Status status, Entity entityType, String taskId, String runId, long runNumber) {

        if (job == null || status == null || entityType == null) {

            logger.info("Invalid event for project sync" );
            return;
        }


        Event event = new Event();
        //event.setId(project.getId());

        event.setTaskId(taskId);

        event.setName(job.getProject().getName() +  "/" +job.getName() + "/" + runNumber);
        event.setLink("/projects");
        event.setUser(job.getCreatedBy());
        event.setEntityType(entityType);
        event.setEventType(Type.Run);
        event.setEntityId(job.getId());
        event.setLink("/app/projects/" + job.getProject().getId() + "/jobs/" + job.getId() + "/runs/" + runId);

        event.setStatus(status);
        NameDto org = new NameDto();
        org.setName(job.getProject().getOrg().getName());
        org.setId(job.getProject().getOrg().getId());
        event.setOrg(org);


        logger.info("Sending event for publish on job [{}] and status [{}] for task type [{}]" , job.getId(), status.toString(), event.getEventType());
        localEventPublisher.publish(event);
    }

}
