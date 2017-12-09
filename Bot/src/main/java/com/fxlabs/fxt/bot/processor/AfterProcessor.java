package com.fxlabs.fxt.bot.processor;


import com.fxlabs.fxt.bot.amqp.Sender;
import com.fxlabs.fxt.bot.assertions.AssertionContext;
import com.fxlabs.fxt.bot.assertions.AssertionLogger;
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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AfterProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private AssertionValidator assertionValidator;
    private RestTemplateUtil restTemplateUtil;
    private OperandEvaluator operandEvaluator;
    private DataResolver dataResolver;

    @Autowired
    public AfterProcessor(AssertionValidator assertionValidator, RestTemplateUtil restTemplateUtil,
                          OperandEvaluator operandEvaluator, DataResolver dataResolver) {
        this.assertionValidator = assertionValidator;
        this.restTemplateUtil = restTemplateUtil;
        this.operandEvaluator = operandEvaluator;
        this.dataResolver = dataResolver;
    }

    public void process(BotTask task, AssertionContext context) {

        if (task == null || task.getEndpoint() == null) {
            return;
        }
        logger.info("Executing after task [{}]", task.getEndpoint());
        //logger.info("{} {} {} {}", task.getEndpoint(), task.getRequest(), task.getUsername(), task.getPassword());

        // TODO - Data Injection

        String url = dataResolver.resolve(task.getEndpoint(), context);

        HttpMethod method = HttpMethodConverter.convert(task.getMethod());

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");

        HeaderUtils.copyHeaders(httpHeaders, task.getHeaders());

        if (StringUtils.isNotEmpty(task.getAuthType())) {
            httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(task.getUsername(), task.getPassword()));
        }

        logger.info("Suite [{}] Total tests [{}] auth [{}]", task.getProjectDataSetId(), task.getRequest().size(), task.getAuthType());

        if (CollectionUtils.isEmpty(task.getRequest())) {
            ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, null);
            if (response != null && response.getStatusCodeValue() != 200) {
                context.getLogs().append(String.format("After StatusCode: [%s]", response.getStatusCode()));
            }
        }

        task.getRequest().parallelStream().forEach(req -> {
            // TODO - Data Injection (req)
            req = dataResolver.resolve(req, context);
            ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, req);
            context.getLogs().append(String.format("After StatusCode: [%s]", response.getStatusCode()));
        });

    }

}
