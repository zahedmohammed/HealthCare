package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.converters.project.PoliciesConverter;
import com.fxlabs.fxt.dao.entity.project.Auth;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dto.project.HttpMethod;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.RunConstants;
import com.fxlabs.fxt.services.amqp.sender.BotClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@Transactional
public class RunTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private BotClientService botClientService;
    private TestSuiteRepository testSuiteRepository;
    private RunRepository runRepository;
    private PoliciesConverter policiesConverter;

    public RunTaskRequestProcessor(BotClientService botClientService, TestSuiteRepository testSuiteRepository,
                                   RunRepository runRepository, PoliciesConverter policiesConverter) {
        this.botClientService = botClientService;
        this.testSuiteRepository = testSuiteRepository;
        this.runRepository = runRepository;
        this.policiesConverter = policiesConverter;
    }

    public void process() {

        //logger.info("started...");
        List<com.fxlabs.fxt.dao.entity.run.Run> runs = runRepository.findByStatus(TaskStatus.WAITING);

        runs.parallelStream().forEach(run -> {

            run.getTask().setStatus(TaskStatus.PROCESSING);
            runRepository.saveAndFlush(run);


            Environment env = validateEnvironment(run);
            if (env == null) return;

            final String region = validateRegion(run);
            if (region == null) return;


            logger.info("Sending task to region [{}]...", region);

            // TODO - Filter Suites by Tags
            Stream<TestSuite> list = testSuiteRepository.findByProjectIdAndType(run.getJob().getProject().getId(), TestSuiteType.SUITE);

            list.forEach(testSuite -> {
                //logger.info("Request {}", i.incrementAndGet());

                try {
                    BotTask task = new BotTask();
                    task.setId(run.getId());
                    task.setSuiteName(testSuite.getName());
                    task.setProjectDataSetId(testSuite.getId());

                    task.setPolicies(policiesConverter.convertToDto(testSuite.getPolicies()));

                    task.setMethod(convert(testSuite.getMethod()));

                    copyHeaders(task, testSuite);

                    copyRequests(task, testSuite);

                    copyAuth(run, env, task, testSuite);

                    copyAssertions(task, testSuite);

                    task.setEndpoint(env.getBaseUrl() + testSuite.getEndpoint());

                    // init & cleanup copy
                    copy(testSuite.getInit(), task.getInit(), run, env);
                    copy(testSuite.getCleanup(), task.getCleanup(), run, env);

                    botClientService.sendTask(task, region);
                } catch (RuntimeException ex) {
                    logger.warn(ex.getLocalizedMessage(), ex);
                }
            });
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
        for (String header : ds.getHeaders()) {
            headers.add(header);
        }
        task.setHeaders(headers);
    }

    private void copyRequests(BotTask task, TestSuite ds) {
        // TODO - JPA lazy-load work-around
        List<String> requests = new ArrayList<>();
        for (String request : ds.getRequests()) {
            requests.add(request);
        }
        task.setRequest(requests);
    }

    private void copyAuth(Run run, Environment env, BotTask task, TestSuite ds) {
        // if empty resolves it to Default
        // if NONE resolves it to none.
        // if value then finds and injects
        List<Auth> creds = env.getAuths();
        if (StringUtils.isEmpty(ds.getAuth())) {
            for (Auth cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), "default")) {
                    task.setAuthType(cred.getAuthType());
                    task.setUsername(cred.getUsername());
                    task.setPassword(cred.getPassword());
                }
            }
        } else if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(ds.getAuth(), "none")) {
            // don't send auth
        } else {
            for (Auth cred : creds) {
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(cred.getName(), ds.getAuth())) {
                    task.setAuthType(cred.getAuthType());
                    task.setUsername(cred.getUsername());
                    task.setPassword(cred.getPassword());
                }
            }
        }
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

            afterTask.setEndpoint(env.getBaseUrl() + suite1.getEndpoint());

            tasks.add(afterTask);
        }
    }

    private Environment findEvn(List<Environment> envs, String env) {
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

        Environment env = findEvn(run.getJob().getProject().getEnvironments(), envName);
        // TODO - Fail if not a valid env.
        if (env == null) {
            run.getTask().setStatus(TaskStatus.FAIL);
            run.getTask().setDescription(String.format("Invalid Env: %s", envName));
            runRepository.saveAndFlush(run);
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
        return region;
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
