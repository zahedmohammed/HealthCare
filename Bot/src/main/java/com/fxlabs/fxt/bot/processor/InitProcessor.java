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
public class InitProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private AssertionValidator assertionValidator;
    private RestTemplateUtil restTemplateUtil;
    private OperandEvaluator operandEvaluator;
    private DataResolver dataResolver;
    private HeaderUtils headerUtils;

    @Autowired
    public InitProcessor(AssertionValidator assertionValidator, RestTemplateUtil restTemplateUtil,
                         OperandEvaluator operandEvaluator, DataResolver dataResolver, HeaderUtils headerUtils) {
        this.assertionValidator = assertionValidator;
        this.restTemplateUtil = restTemplateUtil;
        this.operandEvaluator = operandEvaluator;
        this.dataResolver = dataResolver;
        this.headerUtils = headerUtils;
    }

    public void process(BotTask task, Context context) {

        if (task == null || task.getEndpoint() == null) {
            return;
        }
        logger.debug("Executing after task [{}]", task.getEndpoint());
        //logger.info("{} {} {} {}", task.getEndpoint(), task.getRequest(), task.getUsername(), task.getPassword());

        // Data Injection
        String url = dataResolver.resolve(task.getEndpoint(), context, task.getSuiteName());

        HttpMethod method = HttpMethodConverter.convert(task.getMethod());

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");

        headerUtils.copyHeaders(httpHeaders, task.getHeaders(), context, task.getSuiteName());

        if (StringUtils.equalsIgnoreCase(task.getAuthType(), "basic")) {
            httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(task.getUsername(), task.getPassword()));
        }

        logger.debug("Suite [{}] Total tests [{}] auth [{}]", task.getProjectDataSetId(), task.getTestCases().size(), task.getAuthType());

        AtomicInteger idx = new AtomicInteger(0);
        if (CollectionUtils.isEmpty(task.getTestCases())) {
            logger.info("Executing Suite Init for task [{}] and url [{}]", task.getSuiteName(), url);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, null);
            stopWatch.stop();
            Long time = stopWatch.getTime(TimeUnit.MILLISECONDS);

            Integer size = 0;
            if (StringUtils.isNotEmpty(response.getBody())) {
                size = response.getBody().getBytes().length;
            }

            Context initContext = new Context(context);
            initContext.withSuiteData(url, null, response.getBody(), String.valueOf(response.getStatusCodeValue()), response.getHeaders(), time, size);

            assertionValidator.validate(task.getAssertions(), initContext);

            /*if (response != null && response.getStatusCodeValue() != 200) {
                context.getLogs().append(String.format("After StatusCode: [%s]", response.getStatusCode()));
            }*/

            context.withRequest(task.getSuiteName() + "_Request", null)
                    .withResponse(task.getSuiteName() + "_Response", response.getBody())
                    .withHeaders(task.getSuiteName() + "_Headers", response.getHeaders())
                    .withTask(task);

            context.withRequest(task.getSuiteName() + "_Request[0]", null)
                    .withResponse(task.getSuiteName() + "_Response[0]", response.getBody())
                    .withHeaders(task.getSuiteName() + "_Headers[0]", response.getHeaders())
                    .withTask(task);
        } else {
            // TODO - Support request array
            boolean isOneReq = task.getTestCases().size() == 1;
            task.getTestCases().parallelStream().forEach(testCase -> {
                // Data Injection (req)
                String req = dataResolver.resolve(testCase.getBody(), context, task.getSuiteName());
                logger.info("Executing Suite Init for task [{}] and url [{}]", task.getSuiteName(), url);
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, req);
                stopWatch.stop();
                Long time = stopWatch.getTime(TimeUnit.MILLISECONDS);

                Integer size = 0;
                if (StringUtils.isNotEmpty(response.getBody())) {
                    size = response.getBody().getBytes().length;
                }

                Context initContext = new Context(context);
                initContext.withSuiteData(url, req, response.getBody(), String.valueOf(response.getStatusCodeValue()), response.getHeaders(), time, size);
                assertionValidator.validate(task.getAssertions(), initContext);

                //context.getLogs().append(String.format("After StatusCode: [%s]", response.getStatusCode()));

                //if (isOneReq) {
                context.withRequest(task.getSuiteName() + "_Request", req)
                        .withResponse(task.getSuiteName() + "_Response", response.getBody())
                        .withHeaders(task.getSuiteName() + "_Headers", response.getHeaders())
                        .withTask(task);
                //}

                context.withRequest(task.getSuiteName() + "_Request[" + idx.getAndIncrement() + "]", req)
                        .withResponse(task.getSuiteName() + "_Response[" + idx.getAndIncrement() + "]", response.getBody())
                        .withHeaders(task.getSuiteName() + "_Headers[" + idx.getAndIncrement() + "]", response.getHeaders())
                        .withTask(task);

            });

        }
    }

}
