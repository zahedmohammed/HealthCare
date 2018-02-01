package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.bot.assertions.Context;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class HeaderUtils {

    @Autowired
    private DataResolver dataResolver;

    public void copyHeaders(HttpHeaders httpHeaders, List<String> headers, Context context, String suite) {
        for (String header : headers) {
            String[] tokens = StringUtils.split(header, ":");
            if (ArrayUtils.isNotEmpty(tokens) && tokens.length == 2) {
                String value = dataResolver.resolve(tokens[1].trim(), context, suite);
                httpHeaders.set(tokens[0].trim(), value);
            }
        }
    }
}
