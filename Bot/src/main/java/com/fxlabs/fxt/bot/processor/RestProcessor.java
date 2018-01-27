package com.fxlabs.fxt.bot.processor;


import com.fxlabs.fxt.bot.amqp.Sender;
import com.fxlabs.fxt.bot.assertions.Context;
import com.fxlabs.fxt.bot.assertions.AssertionLogger;
import com.fxlabs.fxt.bot.assertions.AssertionValidator;
import com.fxlabs.fxt.dto.run.BotTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

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

    @Autowired
    RestProcessor(Sender sender, AssertionValidator assertionValidator, RestTemplateUtil restTemplateUtil,
                  InitProcessor initProcessor, CleanUpProcessor cleanUpProcessor, DataResolver dataResolver) {
        this.sender = sender;
        this.assertionValidator = assertionValidator;
        this.restTemplateUtil = restTemplateUtil;
        this.initProcessor = initProcessor;
        this.cleanUpProcessor = cleanUpProcessor;
        this.dataResolver = dataResolver;
    }

    public void process(BotTask task) {

        //logger.info("{}", i.incrementAndGet());
        if (task == null || task.getId() == null || task.getEndpoint() == null) {
            logger.warn("Skipping empty task");
            return;
        }

        BotTask completeTask = new BotTask();

        try {
            completeTask.setId(task.getId());
            completeTask.setProjectDataSetId(task.getProjectDataSetId());
            completeTask.setRequestStartTime(new Date());

            AtomicLong totalFailed = new AtomicLong(0L);
            AtomicLong totalPassed = new AtomicLong(0L);

            AssertionLogger logs = new AssertionLogger();

            String logType = null;
            if (task.getPolicies() != null) {
                logType = task.getPolicies().getLogger();
            }
            Context parentContext = new Context(task.getSuiteName(), logs, logType);


            // execute init
            if (task.getPolicies() != null && StringUtils.equalsIgnoreCase(task.getPolicies().getInitExec(), "Suite")) {
                if (task.getInit() != null) {
                    task.getInit().stream().forEach(t -> {
                        logger.info("Executing Init-Suite for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                        initProcessor.process(t, parentContext);
                    });
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

            HeaderUtils.copyHeaders(httpHeaders, task.getHeaders());


            if (StringUtils.isNotEmpty(task.getAuthType())) {
                httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(task.getUsername(), task.getPassword()));
            }

            logger.info("Suite [{}] Total tests [{}] auth [{}]", task.getProjectDataSetId(), task.getRequest().size(), task.getAuthType());

            // handle GET requests
            if (CollectionUtils.isEmpty(task.getRequest())) {
                task.setRequest(Collections.singletonList(new String("")));
            }

            task.getRequest().parallelStream().forEach(req -> {

                Context context = new Context(parentContext);

                logger.debug("Init {}", task.getCleanup());
                // execute init
                if (task.getPolicies() == null || StringUtils.isEmpty(task.getPolicies().getInitExec())
                        || StringUtils.equalsIgnoreCase(task.getPolicies().getInitExec(), "Request")) {
                    if (task.getInit() != null) {
                        task.getInit().stream().forEach(t -> {
                            logger.info("Executing Suite Init-Request for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                            initProcessor.process(t, context);
                        });
                    }
                }

                //BotTask newTask = new BotTask();
                //newTask.setId(task.getId());
                //newTask.setRequestStartTime(new Date());

                //logger.info("Request: [{}]", req);
                HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

                String endpoint = task.getEndpoint();
                req = dataResolver.resolve(req, parentContext, task.getSuiteName());
                String url = dataResolver.resolve(task.getEndpoint(), parentContext, task.getSuiteName());
                ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, req);

                //newTask.setRequestEndTime(new Date());
                //newTask.setRequestTime(newTask.getRequestEndTime().getTime() - newTask.getRequestStartTime().getTime());

                // validate assertions

                context.withSuiteData(req, response.getBody(), String.valueOf(response.getStatusCodeValue()), response.getHeaders());

                assertionValidator.validate(task.getAssertions(), context);

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
                        task.getCleanup().stream().forEach(t -> {
                            logger.info("Executing Cleanup-Request for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                            cleanUpProcessor.process(t, context, StringUtils.EMPTY);
                        });
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
            });

            if (task.getPolicies() != null && StringUtils.equalsIgnoreCase(task.getPolicies().getCleanupExec(), "Suite")) {
                if (task.getCleanup() != null) {
                    task.getCleanup().stream().forEach(t -> {
                        logger.info("Executing Cleanup-Suite for task [{}] and init [{}]", task.getSuiteName(), t.getSuiteName());
                        cleanUpProcessor.process(t, parentContext, StringUtils.EMPTY);
                    });
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
            completeTask.setTotalTests((long) task.getRequest().size());

            completeTask.setLogs(logs.getLogs());

            completeTask.setRequestEndTime(new Date());
            completeTask.setRequestTime(completeTask.getRequestEndTime().getTime() - completeTask.getRequestStartTime().getTime());
            completeTask.setResult("SUITE");

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            completeTask.setRequestEndTime(new Date());
            completeTask.setLogs(completeTask.getLogs() + "\n " + ex.getLocalizedMessage());
        }
        sender.sendTask(completeTask);

    }


}
