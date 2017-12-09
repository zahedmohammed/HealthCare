package com.fxlabs.fxt.bot.processor;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import java.util.List;

public class HeaderUtils {

    public static void copyHeaders(HttpHeaders httpHeaders, List<String> headers) {
        for (String header : headers) {
            String[] tokens = StringUtils.split(header, ":");
            if (ArrayUtils.isNotEmpty(tokens) && tokens.length == 2) {
                httpHeaders.set(tokens[0].trim(), tokens[1].trim());
            }
        }
    }
}
