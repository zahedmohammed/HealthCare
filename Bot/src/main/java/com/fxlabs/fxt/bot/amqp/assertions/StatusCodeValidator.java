package com.fxlabs.fxt.bot.amqp.assertions;

import com.fxlabs.fxt.dto.run.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.util.List;

@Component
public class StatusCodeValidator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public void validate(String assertion, int expectedStatusCode, StringBuilder status, StringBuilder logs) {

        //logger.warn("validate...");

        if (assertion.matches(".*==.*")) {
            assertEqual(Integer.parseInt(assertion.split("==")[1].trim()), expectedStatusCode, status, logs);
        } else if (assertion.matches(".*!=.*")) {
            assertEqual(Integer.parseInt(assertion.split("!=")[1].trim()), expectedStatusCode, status, logs);
        } else {
            logger.warn("Invalid assertion [{}]", assertion);
            logs.append(String.format("Invalid assertion [%s]\n", assertion));
            status.append("skip");
        }
    }

    private void assertEqual(int code, int expectedStatusCode, StringBuilder status, StringBuilder logs) {
        if (code == expectedStatusCode) {
            status.append("pass");
        } else {
            status.append("fail");
            logs.append(String.format("Expected statusCode [%s], but was [%s]", expectedStatusCode, code));
        }
    }

    private void assertNotEqual(int code, int expectedStatusCode, StringBuilder status, StringBuilder logs) {
        if (code != expectedStatusCode) {
            status.append("pass");
        } else {
            status.append("fail");
            logs.append(String.format("Expected statusCode [%s], but was [%s]", expectedStatusCode, code));
        }
    }
}
