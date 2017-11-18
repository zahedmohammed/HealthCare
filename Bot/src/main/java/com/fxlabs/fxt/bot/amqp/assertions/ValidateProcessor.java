package com.fxlabs.fxt.bot.amqp.assertions;

import com.fxlabs.fxt.dto.run.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class ValidateProcessor {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StatusCodeValidator statusCodeValidator;

    public void process(List<String> assertions, ResponseEntity<String> response, StringBuilder logs, StringBuilder status) {

        // validate assertions collection
        if (CollectionUtils.isEmpty(assertions)) {
            logger.warn("Empty assertions");
            return;
        }

        // validate response
        if (response == null) {
            logger.warn("Null response ");
            logs.append(String.format("Null response from the server \n"));
            status.append("fail");
            return;
        }

        for (String assertion : assertions) {

            // validate assertion
            if (StringUtils.isEmpty(assertion)) {
                logger.warn("Empty assertion");
                continue;
            }

            // extract validator type
            if (assertion.matches("@Response.statusCode.*")) {
                statusCodeValidator.validate(assertion, response.getStatusCodeValue(), status, logs);
            } else {
                logger.warn("Invalid assertion [{}]", assertion);
                logs.append(String.format("Invalid assertion [%s]", assertion));
            }
        }
    }
}
