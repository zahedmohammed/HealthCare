package com.fxlabs.fxt.bot.processor;


import com.fxlabs.fxt.bot.assertions.Context;
import com.fxlabs.fxt.bot.assertions.AssertionValidator;
import com.fxlabs.fxt.bot.validators.OperandEvaluator;
import com.fxlabs.fxt.dto.run.BotTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CleanUpProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private AssertionValidator assertionValidator;
    private RestTemplateUtil restTemplateUtil;
    private OperandEvaluator operandEvaluator;
    private DataResolver dataResolver;

    @Autowired
    public CleanUpProcessor(AssertionValidator assertionValidator, RestTemplateUtil restTemplateUtil,
                            OperandEvaluator operandEvaluator, DataResolver dataResolver) {
        this.assertionValidator = assertionValidator;
        this.restTemplateUtil = restTemplateUtil;
        this.operandEvaluator = operandEvaluator;
        this.dataResolver = dataResolver;
    }

    public void process(BotTask task, Context context, String parentSuite) {

        if (task == null || task.getEndpoint() == null) {
            return;
        }
        logger.debug("Executing after task [{}]", task.getEndpoint());
        //logger.info("{} {} {} {}", task.getEndpoint(), task.getRequest(), task.getUsername(), task.getPassword());

        // Data Injection
        String url = dataResolver.resolve(task.getEndpoint(), context, parentSuite);

        HttpMethod method = HttpMethodConverter.convert(task.getMethod());

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");

        HeaderUtils.copyHeaders(httpHeaders, task.getHeaders());

        if (StringUtils.isNotEmpty(task.getAuthType())) {
            httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(task.getUsername(), task.getPassword()));
        }

        logger.debug("Suite [{}] Total tests [{}] auth [{}]", task.getProjectDataSetId(), task.getRequest().size(), task.getAuthType());

        AtomicInteger idx = new AtomicInteger(0);
        if (CollectionUtils.isEmpty(task.getRequest())) {
            ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, null);
            if (response != null && response.getStatusCodeValue() != 200) {
                context.getLogs().append(String.format("After StatusCode: [%s]", response.getStatusCode()));
            }
        } else {
            // TODO - Support request array
            task.getRequest().parallelStream().forEach(req -> {
                // Data Injection (req)
                req = dataResolver.resolve(req, context, parentSuite);
                ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, req);
                context.getLogs().append(String.format("After StatusCode: [%s]", response.getStatusCode()));
            });

        }
    }

}
