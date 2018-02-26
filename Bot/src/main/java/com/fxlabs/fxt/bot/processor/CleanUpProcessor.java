package com.fxlabs.fxt.bot.processor;


import com.fxlabs.fxt.bot.assertions.AssertionValidator;
import com.fxlabs.fxt.bot.assertions.Context;
import com.fxlabs.fxt.bot.validators.OperandEvaluator;
import com.fxlabs.fxt.dto.run.BotTask;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class CleanUpProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private AssertionValidator assertionValidator;
    private RestTemplateUtil restTemplateUtil;
    private OperandEvaluator operandEvaluator;
    private DataResolver dataResolver;
    private HeaderUtils headerUtils;

    @Autowired
    public CleanUpProcessor(AssertionValidator assertionValidator, RestTemplateUtil restTemplateUtil,
                            OperandEvaluator operandEvaluator, DataResolver dataResolver, HeaderUtils headerUtils) {
        this.assertionValidator = assertionValidator;
        this.restTemplateUtil = restTemplateUtil;
        this.operandEvaluator = operandEvaluator;
        this.dataResolver = dataResolver;
        this.headerUtils = headerUtils;
    }

    public void process(BotTask task, Context parentContext, String parentSuite) {
        if (task.getPolicies() != null && task.getPolicies().getRepeat() != null && task.getPolicies().getRepeat() > 0) {
            for (int i = 0; i < task.getPolicies().getRepeat(); i++) {
                run(task, parentContext, parentSuite);

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

                run(task, parentContext, parentSuite);

                if (StringUtils.equalsIgnoreCase(parentContext.getResult(), "pass")) {
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
            run(task, parentContext, parentSuite);

        }
    }

    private void run(BotTask task, Context parentContext, String parentSuite) {

        if (task == null || task.getEndpoint() == null) {
            return;
        }
        logger.debug("Executing after task [{}]", task.getEndpoint());
        //logger.info("{} {} {} {}", task.getEndpoint(), task.getRequest(), task.getUsername(), task.getPassword());

        Context context = new Context(parentContext, task.getSuiteName());
        // Data Injection
        String url = dataResolver.resolve(task.getEndpoint(), parentContext, parentSuite);

        HttpMethod method = HttpMethodConverter.convert(task.getMethod());

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");

        headerUtils.copyHeaders(httpHeaders, task.getHeaders(), context, task.getSuiteName());

        if (StringUtils.equalsIgnoreCase(task.getAuthType(), "basic")) {
            httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(task.getUsername(), task.getPassword()));
        }

        logger.info("Suite [{}] Total tests [{}] auth [{}] url [{}]", task.getSuiteName(), task.getTestCases().size(), task.getAuthType(), url);

        AtomicInteger idx = new AtomicInteger(0);
        if (CollectionUtils.isEmpty(task.getTestCases())) {
            logger.info("Executing Suite Cleanup for task [{}] and url [{}]", task.getSuiteName(), url);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, null);
            stopWatch.stop();
            Long time = stopWatch.getTime(TimeUnit.MILLISECONDS);

            Integer size = 0;
            if (StringUtils.isNotEmpty(response.getBody())) {
                size = response.getBody().getBytes().length;
            }
            logger.info("Suite [{}] Total tests [{}] auth [{}] url [{}] status [{}]", task.getSuiteName(), task.getTestCases().size(), task.getAuthType(), url, response.getStatusCode());
            context.withSuiteDataForPostProcessor(url, null, response.getBody(), String.valueOf(response.getStatusCodeValue()), response.getHeaders(), time, size);

            assertionValidator.validate(task.getAssertions(), context);

            /*if (response != null && response.getStatusCodeValue() != 200) {
                context.getLogs().append(String.format("After StatusCode: [%s]", response.getStatusCode()));
            }*/

        } else {
            // TODO - Support request array
            task.getTestCases().parallelStream().forEach(testCase -> {
                // Data Injection (req)
                String req = dataResolver.resolve(testCase.getBody(), context, parentSuite);
                logger.info("Executing Suite Cleanup for task [{}] and url [{}]", task.getSuiteName(), url);
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, req);
                stopWatch.stop();
                Long time = stopWatch.getTime(TimeUnit.MILLISECONDS);

                Integer size = 0;
                if (StringUtils.isNotEmpty(response.getBody())) {
                    size = response.getBody().getBytes().length;
                }
                context.withSuiteDataForPostProcessor(url, req, response.getBody(), String.valueOf(response.getStatusCodeValue()), response.getHeaders(), time, size);
                assertionValidator.validate(task.getAssertions(), context);
                //context.getLogs().append(String.format("After StatusCode: [%s]", response.getStatusCode()));
            });

        }
    }

}
