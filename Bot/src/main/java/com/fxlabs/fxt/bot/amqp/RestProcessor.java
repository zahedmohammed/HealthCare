package com.fxlabs.fxt.bot.amqp;


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

    @Autowired
    RestProcessor(Sender sender) {
        this.sender = sender;
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

        for (String req : task.getRequest()) {

            BotTask newTask = new BotTask();
            newTask.setId(task.getId());
            newTask.setRequestStartTime(new Date());
            newTask.setSuccess(true);

            //logger.info("Request: [{}]", req);
            HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

            ResponseEntity<String> response = null;
            try {
                response = restTemplate.exchange(url, method, request, String.class);
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage());
            }
            newTask.setRequestEndTime(new Date());

            // validate assertions

            if (response == null || response.getStatusCode() != HttpStatus.OK) {
                newTask.setLogs(String.format("Expected http status code [%s], but was [%s]", HttpStatus.OK.value(), response));
                newTask.setSuccess(false);
                logger.info("Request [{}] response [{}]", req, response);
            } else {
                // compose response
                newTask.setResponse(new ArrayList<>());
                newTask.getResponse().add(response.getBody());
                logger.info("Request [{}] status-code [{}] response-body [{}]", req, response.getStatusCode(), response.getBody());
            }

            // return processed task
            sender.sendTask(newTask);
        }


    }

    String createBasicAuth(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;

    }


}
