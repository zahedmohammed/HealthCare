package com.fxlabs.fxt.codegen.generators.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class JsonFormatUtil {
    private final static ObjectMapper mapper = new ObjectMapper();
    final static Logger logger = LoggerFactory.getLogger(JsonFormatUtil.class);

    public static String format(String value) {

        if (StringUtils.isEmpty(value)) {
            return value;
        }

        try {
            Object jsonObject = mapper.readValue(value, Object.class);
            String result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            // result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
            return result;
        } catch (IOException e) {
            logger.warn(e.getLocalizedMessage());
        }
        return value;
    }

    public static String replaceQuotes(String value) {

        if (StringUtils.isEmpty(value)) {
            return value;
        }

        value = format(value);
        value = StringUtils.replace(value, "\"", "'");

        return value;
    }

    public static String clean(String value) {
        return StringUtils.removeAll(value, "\u0000");
    }
}
