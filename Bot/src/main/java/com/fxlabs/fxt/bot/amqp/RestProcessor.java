package com.fxlabs.fxt.bot.amqp;


import com.fxlabs.fxt.bot.amqp.assertions.ValidateProcessor;
import com.fxlabs.fxt.dto.run.BotTask;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

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

        //logger.info("{} {} {} {}", task.getEndpoint(), task.getRequest(), task.getUsername(), task.getPassword());

        // execute request
        RestTemplate restTemplate = new RestTemplate();
        String url = task.getEndpoint();
        HttpMethod method = HttpMethod.POST;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", createBasicAuth(task.getUsername(), task.getPassword()));

        logger.info("Total tests [{}]", task.getRequest().size());

        for (String req : task.getRequest()) {

            BotTask newTask = new BotTask();
            newTask.setId(task.getId());
            newTask.setRequestStartTime(new Date());

            //logger.info("Request: [{}]", req);
            HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

            ResponseEntity<String> response = null;
            try {
                response = restTemplate.exchange(url, method, request, String.class);
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage(), e);
                response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            newTask.setRequestEndTime(new Date());
            newTask.setRequestTime(newTask.getRequestEndTime().getTime() - newTask.getRequestStartTime().getTime());

            // validate assertions

            StringBuilder logs = new StringBuilder();
            StringBuilder taskStatus = new StringBuilder();
            validatorProcessor.process(task.getAssertions(), response, logs, taskStatus);
            newTask.setLogs(logs.toString());
            newTask.setResult(taskStatus.toString());

            // return processed task
            sender.sendTask(newTask);
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
