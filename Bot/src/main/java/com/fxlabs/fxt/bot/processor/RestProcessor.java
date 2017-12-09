package com.fxlabs.fxt.bot.processor;


import com.fxlabs.fxt.bot.amqp.Sender;
import com.fxlabs.fxt.bot.assertions.AssertionContext;
import com.fxlabs.fxt.bot.assertions.AssertionLogger;
import com.fxlabs.fxt.bot.assertions.AssertionValidator;
import com.fxlabs.fxt.dto.run.BotTask;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RestProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private Sender sender;
    //private ValidateProcessor validatorProcessor;
    private AssertionValidator assertionValidator;
    private RestTemplateUtil restTemplateUtil;
    private AfterProcessor afterProcessor;

    @Autowired
    RestProcessor(Sender sender, AssertionValidator assertionValidator, RestTemplateUtil restTemplateUtil,
                  AfterProcessor afterProcessor) {
        this.sender = sender;
        this.assertionValidator = assertionValidator;
        this.restTemplateUtil = restTemplateUtil;
        this.afterProcessor = afterProcessor;
    }

    //AtomicInteger i = new AtomicInteger(1);

    public void process(BotTask task) {

        //logger.info("{}", i.incrementAndGet());
        if (task == null || task.getId() == null || task.getEndpoint() == null) {
            return;
        }

        BotTask completeTask = new BotTask();
        completeTask.setId(task.getId());
        completeTask.setProjectDataSetId(task.getProjectDataSetId());
        completeTask.setRequestStartTime(new Date());
        AtomicLong totalFailed = new AtomicLong(0L);
        //AtomicLong totalSkipped = new AtomicLong(0L);
        AtomicLong totalPassed = new AtomicLong(0L);
        AssertionLogger logs = new AssertionLogger();

        //logger.info("{} {} {} {}", task.getEndpoint(), task.getRequest(), task.getUsername(), task.getPassword());

        // execute request
        //RestTemplate restTemplate = new RestTemplate();
        String url = task.getEndpoint();
        HttpMethod method = HttpMethodConverter.convert(task.getMethod());
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");

        HeaderUtils.copyHeaders(httpHeaders, task.getHeaders());


        if (StringUtils.isNotEmpty(task.getAuthType())) {
            httpHeaders.set("Authorization", AuthBuilder.createBasicAuth(task.getUsername(), task.getPassword()));
        }

        logger.info("Suite [{}] Total tests [{}] auth [{}]", task.getProjectDataSetId(), task.getRequest().size(), task.getAuthType());

        task.getRequest().parallelStream().forEach(req -> {

            //BotTask newTask = new BotTask();
            //newTask.setId(task.getId());
            //newTask.setRequestStartTime(new Date());

            //logger.info("Request: [{}]", req);
            HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

            ResponseEntity<String> response = restTemplateUtil.execRequest(url, method, httpHeaders, req);

            //newTask.setRequestEndTime(new Date());
            //newTask.setRequestTime(newTask.getRequestEndTime().getTime() - newTask.getRequestStartTime().getTime());

            // validate assertions

            AssertionContext context = new AssertionContext(req, response.getBody(), String.valueOf(response.getStatusCodeValue()), response.getHeaders(), logs);
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

            logger.info("After {}", task.getAfter());
            // TODO - execute after
            if (task.getAfter() != null) {
                task.getAfter().stream().forEach(t -> {
                    afterProcessor.process(t, context);
                });
            }


            // return processed task
            //sender.sendTask(newTask);
        });

        // send test suite complete

        completeTask.setTotalFailed(totalFailed.get());
        //completeTask.setTotalSkipped(totalSkipped.get());
        completeTask.setTotalPassed(totalPassed.get());
        completeTask.setTotalTests((long) task.getRequest().size());

        completeTask.setLogs(logs.getLogs());

        completeTask.setRequestEndTime(new Date());
        completeTask.setRequestTime(completeTask.getRequestEndTime().getTime() - completeTask.getRequestStartTime().getTime());
        completeTask.setResult("SUITE");

        sender.sendTask(completeTask);

    }


}
