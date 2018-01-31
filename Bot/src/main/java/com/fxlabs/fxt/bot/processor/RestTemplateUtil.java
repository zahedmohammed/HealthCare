package com.fxlabs.fxt.bot.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateUtil {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public ResponseEntity<String> execRequest(String url, HttpMethod method, HttpHeaders httpHeaders, String req) {
        // execute request
        RestTemplate restTemplate = new RestTemplate();

        //logger.info("Request: [{}]", req);
        HttpEntity<String> request = new HttpEntity<>(req, httpHeaders);

        ResponseEntity<String> response = null;
        int statusCode = -1;
        String responseBody = null;
        HttpHeaders headers = null;
        try {
            response = restTemplate.exchange(url, method, request, String.class);
            //statusCode = response.getStatusCodeValue();
            //responseBody = response.getBody();
            //headers = response.getHeaders();
        } catch (HttpStatusCodeException statusCodeException) {
            response = new ResponseEntity<String>(statusCodeException.getResponseHeaders(), statusCodeException.getStatusCode());
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
        }

        return response;
    }
}