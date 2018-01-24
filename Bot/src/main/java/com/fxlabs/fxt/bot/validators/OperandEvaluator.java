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
            String KEY = tokens[0];
            String PATH = null;//"$..*";
            String PIPE = "";

            if (tokens.length == 2 && !StringUtils.containsIgnoreCase(KEY, "Headers")) {
                PATH = "$." + tokens[1];
            }

            if (tokens.length == 2 && StringUtils.containsIgnoreCase(KEY, "Headers")) {
                PATH = tokens[1];
            }

            if (StringUtils.contains(PATH, "|")) {
                String[] pipeTokens = StringUtils.split(PATH, "|");
                PATH = StringUtils.trim(pipeTokens[0]);
                if (pipeTokens.length == 2) {
                    PIPE = StringUtils.trim(pipeTokens[1]);
                }
            }

            // Locate suite
            String suiteName = "";
            if (StringUtils.contains(KEY, "_")) {
                suiteName = StringUtils.substringBeforeLast(KEY, "_");
                suiteName = StringUtils.substringAfter(suiteName, "@");
                KEY = "@" + StringUtils.substringAfterLast(KEY, "_");
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
                case "@Suite_Headers":
                case "@Headers":
                    val = context.getHeaders(suiteName).get(PATH).get(0);
                    logger.info("Header [{}]", val);
                    break;

                case "@Suite_Request":
                case "@Request":
                    if (StringUtils.isEmpty(PATH)) {
                        val = context.getRequest(suiteName);
                    } else {
                        Object objRequest = JsonPath.read(context.getRequest(suiteName), PATH);
                        val = objRequest.toString();
                    }
                    break;

                case "@Suite_Response":
                case "@Response":
                    if (StringUtils.isEmpty(PATH)) {
                        val = context.getResponse(suiteName);
                    } else {
                        Object objResponse = JsonPath.read(context.getResponse(suiteName), PATH);
                        val = objResponse.toString();
                    }
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

            if (StringUtils.isNotEmpty(PIPE)) {
                val = evaluatePipe(PIPE, val);
            }

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
        }


        return val;
    }

    /*
     *  http://localhost:8090/example/v1/hotels/14
      *  {{@Headers.Location | substringAfterLast /}}
     */
    private String evaluatePipe(String pipe, String text) {
        String val = text;
        if (StringUtils.isEmpty(pipe)) {
            return val;
        }

        String[] seed = StringUtils.split(pipe);

        String KEY = StringUtils.trim(seed[0]);


        switch (KEY) {
            case "substringAfterLast":
                val = StringUtils.substringAfterLast(text, StringUtils.trim(seed[1]));
                break;
        }

        return val;
    }
}
