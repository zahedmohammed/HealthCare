package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.AssertionContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OperandEvaluator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public String evaluate(String key, AssertionContext context) {
        String val = "";
        try {
            String[] tokens = StringUtils.split(key, ".", 2);
            final String KEY = tokens[0];
            String PATH = "$..*";
            if (tokens.length == 2) {
                PATH = "$." + tokens[1];
            }

            switch (KEY) {
                case "NULL":
                case "null":
                case "EMPTY":
                case "empty":
                    val = "";
                    break;

                case "@StatusCode":
                    val = context.getStatusCode();
                    break;

                // TODO
                //case "@Headers":

                case "@Request":
                    Object objRequest = JsonPath.read(context.getRequest(), PATH);
                    val = objRequest.toString();
                    break;

                case "@Response":
                    Object objResponse = JsonPath.read(context.getResponse(), PATH);
                    val = objResponse.toString();
                    break;

                default:
                    val = key;

            }
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
        }
        return val;
    }
}
