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
        String[] tokens = StringUtils.split(key, ".", 2);
        final String KEY = tokens[0];
        String PATH = "$..*";
        if (tokens.length == 2) {
            PATH = "$." + tokens[1];
        }

        String val = "";
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
                val = JsonPath.read(context.getRequest(), PATH);
                break;

            case "@Response":
                val = JsonPath.read(context.getResponse(), PATH);
                break;

            default:
                val = key;

        }
        return val;
    }
}
