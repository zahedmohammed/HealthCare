package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.Context;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OperandEvaluator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    public String evaluate(String key, Context context, String suite) {
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

                case "@Suite_Request":
                case "@Request":
                    Object objRequest = JsonPath.read(context.getRequest(suite), PATH);
                    val = objRequest.toString();
                    break;

                case "@Suite_Response":
                case "@Response":
                    Object objResponse = JsonPath.read(context.getResponse(suite), PATH);
                    val = objResponse.toString();
                    break;

                default:
                    if (StringUtils.endsWithIgnoreCase(KEY, "_Request") || StringUtils.endsWithIgnoreCase(KEY, "_Response")) {
                        Object initData = JsonPath.read(context.get(KEY), PATH);
                        if (initData != null) {
                            val = initData.toString();
                        }
                    } else {
                        val = key;
                    }

            }
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
        }
        return val;
    }
}
