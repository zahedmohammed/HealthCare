package com.fxlabs.fxt.bot.amqp;


import com.fxlabs.fxt.bot.amqp.assertions.ValidateProcessor;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RestProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private Sender sender;
    private ValidateProcessor validatorProcessor;

    @Autowired
    RestProcessor(Sender sender, ValidateProcessor validatorProcessor) {
        this.sender = sender;
        this.validatorProcessor = validatorProcessor;
    }

    public void process(BotTask task) {

        if (task == null || task.getId() == null || task.getEndpoint() == null) {
            return;
        }

        BotTask completeTask = new BotTask();
        completeTask.setId(task.getId());
        completeTask.setProjectDataSetId(task.getProjectDataSetId());
        completeTask.setRequestStartTime(new Date());

        //logger.info("{} {} {} {}", task.getEndpoint(), task.getRequest(), task.getUsername(), task.getPassword());

        // execute request
        RestTemplate restTemplate = new RestTemplate();
        String url = task.getEndpoint();
        HttpMethod method = HttpMethod.POST;
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");

        copyHeaders(httpHeaders, task.getHeaders());


        if (StringUtils.isNotEmpty(task.getAuthType())) {
            httpHeaders.set("Authorization", createBasicAuth(task.getUsername(), task.getPassword()));
        }

        logger.info("Suite [{}] Total tests [{}] auth [{}]", task.getProjectDataSetId(), task.getRequest().size(), task.getAuthType());

        for (String req : task.getRequest()) {

            BotTask newTask = new BotTask();
            newTask.setId(task.getId());
            newTask.setRequestStartTime(new Date());

            //logger.info("Request: [{}]", req);
            HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

            ResponseEntity<String> response = null;
            int statusCode = -1;
            try {
                response = restTemplate.exchange(url, method, request, String.class);
                statusCode = response.getStatusCodeValue();
            } catch (HttpStatusCodeException statusCodeException) {
                statusCode = statusCodeException.getRawStatusCode();
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage());
                response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            newTask.setRequestEndTime(new Date());
            newTask.setRequestTime(newTask.getRequestEndTime().getTime() - newTask.getRequestStartTime().getTime());

            // validate assertions

            StringBuilder logs = new StringBuilder();
            StringBuilder taskStatus = new StringBuilder();
            validatorProcessor.process(task.getAssertions(), response, statusCode, logs, taskStatus);

            newTask.setLogs(logs.toString());
            newTask.setResult(taskStatus.toString());

            if (StringUtils.equalsIgnoreCase("fail", newTask.getResult())) {
                completeTask.setTotalFailed(completeTask.getTotalFailed() + 1);
            } else if (StringUtils.equalsIgnoreCase("skip", newTask.getResult())) {
                completeTask.setTotalSkipped(completeTask.getTotalSkipped() + 1);
            } else {
                completeTask.setTotalPassed(completeTask.getTotalPassed() + 1);
            }


            // return processed task
            sender.sendTask(newTask);
        }

        // send test suite complete

        completeTask.setRequestEndTime(new Date());
        completeTask.setRequestTime(completeTask.getRequestEndTime().getTime() - completeTask.getRequestStartTime().getTime());
        completeTask.setResult("SUITE");

        sender.sendTask(completeTask);


    }

    private void copyHeaders(HttpHeaders httpHeaders, List<String> headers) {
        for (String header : headers) {
            String[] tokens = StringUtils.split(header, ":");
            if (ArrayUtils.isNotEmpty(tokens) && tokens.length == 2) {
                httpHeaders.set(tokens[0].trim(), tokens[1].trim());
            }
        }
    }

    private String createBasicAuth(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;

    }


}
