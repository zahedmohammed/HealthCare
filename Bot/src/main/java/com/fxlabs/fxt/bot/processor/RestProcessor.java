package com.fxlabs.fxt.bot.processor;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxlabs.fxt.bot.amqp.Sender;
import com.fxlabs.fxt.bot.assertions.AssertionLogger;
import com.fxlabs.fxt.bot.assertions.AssertionValidator;
import com.fxlabs.fxt.bot.assertions.Context;
import com.fxlabs.fxt.dto.project.TestCase;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.Suite;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Component
public class RestProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private Sender sender;
    //private ValidateProcessor validatorProcessor;
    private AssertionValidator assertionValidator;
    private RestTemplateUtil restTemplateUtil;
    private InitProcessor initProcessor;
    private CleanUpProcessor cleanUpProcessor;
    private DataResolver dataResolver;
    private HeaderUtils headerUtils;

    @Autowired
    RestProcessor(Sender sender, AssertionValidator assertionValidator, RestTemplateUtil restTemplateUtil,
                  InitProcessor initProcessor, CleanUpProcessor cleanUpProcessor, DataResolver dataResolver,
                  HeaderUtils headerUtils) {
        this.sender = sender;
        this.assertionValidator = assertionValidator;
        this.restTemplateUtil = restTemplateUtil;
        this.initProcessor = initProcessor;
        this.cleanUpProcessor = cleanUpProcessor;
        this.dataResolver = dataResolver;
        this.headerUtils = headerUtils;
    }

    public void process(BotTask task) {

        if (task.getPolicies() != null && task.getPolicies().getRepeat() != null && task.getPolicies().getRepeat() > 0) {
            for (int i = 0; i < task.getPolicies().getRepeat(); i++) {
                BotTask completeTask = run(task);
                if (completeTask == null) return;
                sender.sendTask(completeTask);
                if (task.getPolicies().getRepeatDelay() != null && task.getPolicies().getRepeatDelay() > 0) {
                    try {
                        Thread.sleep(task.getPolicies().getRepeatDelay());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (task.getPolicies() != null && task.getPolicies().getRepeatOnFailure() != null && task.getPolicies().getRepeatOnFailure() > 0) {
            for (int i = 0; i < task.getPolicies().getRepeatOnFailure(); i++) {
                BotTask completeTask = run(task);
                if (completeTask == null) return;
                if (completeTask.getTotalFailed() <= 0) {
                    sender.sendTask(completeTask);
                    break;
                }
                if (task.getPolicies().getRepeatDelay() != null && task.getPolicies().getRepeatDelay() > 0) {
                    try {
                        Thread.sleep(task.getPolicies().getRepeatDelay());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            BotTask completeTask = run(task);
            if (completeTask == null) return;
            sender.sendTask(completeTask);
        }

    }

    private BotTask run(BotTask task) {
        //logger.info("{}", i.incrementAndGet());
        if (task == null || task.getId() == null || task.getEndpoint() == null) {
            logger.warn("Skipping empty task");
            return null;
        }

        BotTask completeTask = new BotTask();
        final Suite suite = new Suite();
        List<TestCaseResponse> testCaseResponses = new ArrayList<>();
        boolean generateTestCases = task.isGenerateTestCaseResponse();

        AtomicLong totalFailed = new AtomicLong(0L);
        AtomicLong totalPassed = new AtomicLong(0L);
        AtomicLong totalTime = new AtomicLong(0L);
        AtomicLong totalSize = new AtomicLong(0L);

        try {
            suite.setRunId(task.getId());
            suite.setSuiteName(task.getSuiteName());

            // handle GET requests
            if (CollectionUtils.isEmpty(task.getTestCases())) {
                task.setTestCases(Collections.singletonList(new TestCase()));
            }

            completeTask.setId(task.getId());
            completeTask.setProjectDataSetId(task.getProjectDataSetId());
            completeTask.setRequestStartTime(new Date());


            AssertionLogger logs = new AssertionLogger();

            String logType = null;
            if (task.getPolicies() != null) {
                logType = task.getPolicies().getLogger();
            }
            Context parentContext = new Context(task.getSuiteName(), logs, logType);


            // execute init
            if (task.getPolicies() != null && StringUtils.equalsIgnoreCase(task.getPolicies().getInitExec(), "Suite")) {
                if (task.getInit() != null) {
                    //task.getInit().stream().forEach(t -> {
                    for (BotTask t : task.getInit()) {
                        logger.info("Executing Init-Suite for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                        initProcessor.process(t, parentContext);
                    }
                    //);
                }
            }

            //logger.info("{} {} {} {}", task.getEndpoint(), task.getRequest(), task.getUsername(), task.getPassword());

            // execute request
            //RestTemplate restTemplate = new RestTemplate();
            //String url = task.getEndpoint();
            HttpMethod method = HttpMethodConverter.convert(task.getMethod());
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.set("Content-Type", "application/json");
            httpHeaders.set("Accept", "application/json");

            headerUtils.copyHeaders(httpHeaders, task.getHeaders(), parentContext, task.getSuiteName());

            // TODO - handle other auth types
            if (StringUtils.equalsIgnoreCase(task.getAuthType(), "basic")) {
                httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(task.getUsername(), task.getPassword()));
            }

            logger.info("Suite [{}] Total tests [{}] auth [{}]", task.getProjectDataSetId(), task.getTestCases().size(), task.getAuthType());

            task.getTestCases().parallelStream().forEach(testCase -> {
                //for (String req : task.getRequest()) {

                Context context = new Context(parentContext);

                logger.debug("Init {}", task.getCleanup());
                // execute init
                if (task.getPolicies() == null || StringUtils.isEmpty(task.getPolicies().getInitExec())
                        || StringUtils.equalsIgnoreCase(task.getPolicies().getInitExec(), "Request")) {
                    if (task.getInit() != null) {
                        //task.getInit().stream().forEach(t -> {
                        for (BotTask t : task.getInit()) {
                            logger.info("Executing Suite Init-Request for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                            initProcessor.process(t, context);
                        }
                        //);
                    }
                }

                //BotTask newTask = new BotTask();
                //newTask.setId(task.getId());
                //newTask.setRequestStartTime(new Date());

                //logger.info("Request: [{}]", req);
                HttpEntity<String> request = new HttpEntity<>(testCase.getBody(), httpHeaders);

                //String endpoint = task.getEndpoint();
                String req = dataResolver.resolve(testCase.getBody(), parentContext, task.getSuiteName());
                String url = dataResolver.resolve(task.getEndpoint(), parentContext, task.getSuiteName());

                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, req);
                stopWatch.stop();
                Long time = stopWatch.getTime(TimeUnit.MILLISECONDS);
                totalTime.getAndAdd(time);

                Integer size = 0;
                if (StringUtils.isNotEmpty(response.getBody())) {
                    size = response.getBody().getBytes().length;
                }
                totalSize.getAndAdd(size);

                //newTask.setRequestEndTime(new Date());
                //newTask.setRequestTime(newTask.getRequestEndTime().getTime() - newTask.getRequestStartTime().getTime());

                // validate assertions
                context.withSuiteData(url, method.name(), req, httpHeaders, response.getBody(), String.valueOf(response.getStatusCodeValue()), response.getHeaders(), time, size);

                StringBuilder assertionLogs = new StringBuilder();
                assertionValidator.validate(task.getAssertions(), context, assertionLogs);

                //validatorProcessor.process(task.getAssertions(), response, statusCode, logs, taskStatus);

                //newTask.setLogs(context.getLogs().toString());
                //newTask.setResult(context.getResult());

                //logger.info("Result: [{}]", newTask.getResult());
                switch (context.getResult()) {
                    case "pass":
                        totalPassed.incrementAndGet();
                        break;
                    case "fail":
                    default:
                        totalFailed.incrementAndGet();
                        break;
                }

                logger.debug("Cleanup {}", task.getCleanup());
                // execute after
                if (task.getPolicies() == null || StringUtils.isEmpty(task.getPolicies().getCleanupExec())
                        || StringUtils.equalsIgnoreCase(task.getPolicies().getCleanupExec(), "Request")) {
                    if (task.getCleanup() != null) {
                        for (BotTask t : task.getCleanup()) {
                            //task.getCleanup().stream().forEach(t -> {
                            logger.info("Executing Cleanup-Request for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                            cleanUpProcessor.process(t, context, StringUtils.EMPTY);
                        }
                        //);
                    }
                }

                // clean-up init tasks
                if (!CollectionUtils.isEmpty(context.getInitTasks())) {
                    context.getInitTasks().stream().forEach(initTask -> {
                        initTask.getInit().stream().forEach(t -> {
                            logger.info("Executing Cleanup-Init-Request for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                            cleanUpProcessor.process(t, context, t.getSuiteName());
                        });
                    });
                }

                // return processed task
                //sender.sendTask(newTask);
                String requestedFormatted = getFromattedJsonString(req)
                        ;
                if (StringUtils.isEmpty(requestedFormatted)){
                    requestedFormatted = req;
                }

                String responseFormatted = getFromattedJsonString(response.getBody());

                if (StringUtils.isEmpty(responseFormatted)){
                    responseFormatted = response.getBody();
                }

                // Test-Cases Responses
                if (generateTestCases) {
                    TestCaseResponse tc = new TestCaseResponse();
                    tc.setProject(task.getProject());
                    tc.setJob(task.getJob());
                    tc.setJobId(task.getJobId());
                    tc.setEnv(task.getEnv());
                    tc.setRegion(task.getRegion());
                    tc.setSuite(task.getSuiteName());
                    tc.setTestCase(String.valueOf(testCase.getId()));
                    tc.setEndpointEval(url);
                    tc.setRequestEval(requestedFormatted);
                    tc.setResponse(responseFormatted);
                    tc.setStatusCode(String.valueOf(response.getStatusCodeValue()));
                    tc.setResult(context.getResult());
                    tc.setTime(time);
                    tc.setSize(size);
                    tc.setHeaders(response.getHeaders().toString());
                    tc.setLogs(assertionLogs.toString());
                    // TODO - Assertions
                    testCaseResponses.add(tc);
                }

            });

            if (task.getPolicies() != null && StringUtils.equalsIgnoreCase(task.getPolicies().getCleanupExec(), "Suite")) {
                if (task.getCleanup() != null) {
                    //task.getCleanup().stream().forEach(t -> {
                    for (BotTask t : task.getCleanup()) {
                        logger.info("Executing Cleanup-Suite for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                        cleanUpProcessor.process(t, parentContext, StringUtils.EMPTY);
                    }
                    //);
                }
            }

            if (task.getPolicies() != null && StringUtils.equalsIgnoreCase(task.getPolicies().getInitExec(), "Suite")) {
                parentContext.getInitTasks().stream().forEach(initTask -> {
                    initTask.getInit().stream().forEach(t -> {
                        logger.info("Executing Cleanup-Init-Suite for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                        cleanUpProcessor.process(t, parentContext, t.getSuiteName());
                    });
                });
            }

            // send test suite complete

            completeTask.setTotalFailed(totalFailed.get());
            //completeTask.setTotalSkipped(totalSkipped.get());
            completeTask.setTotalPassed(totalPassed.get());
            completeTask.setTotalTests((long) task.getTestCases().size());

            completeTask.setLogs(logs.getLogs());

            completeTask.setRequestEndTime(new Date());
            completeTask.setRequestTime(completeTask.getRequestEndTime().getTime() - completeTask.getRequestStartTime().getTime());
            completeTask.setResult("SUITE");

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            completeTask.setRequestEndTime(new Date());
            completeTask.setLogs(completeTask.getLogs() + "\n " + ex.getLocalizedMessage());
        }

        suite.setFailed(totalFailed.get());
        suite.setTests(totalFailed.get() + totalPassed.get());
        suite.setSize(totalSize.get());
        suite.setTime(totalTime.get());
        this.sender.sendTask(suite);

        if (generateTestCases) {
            this.sender.sendTestCases(testCaseResponses);
        }

        return completeTask;
    }


    private String getFromattedJsonString(String value) {
        ObjectMapper mapper = new ObjectMapper();

        String result = null;
        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            return null;
        }
        return result;
    }



}
