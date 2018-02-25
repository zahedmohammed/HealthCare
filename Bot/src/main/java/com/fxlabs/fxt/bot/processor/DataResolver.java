package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.bot.assertions.Context;
import com.fxlabs.fxt.bot.validators.OperandEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class DataResolver {

    final static String PATTERN = "\\{\\{(.*?)\\}\\}";
    final Logger logger = LoggerFactory.getLogger(getClass());
    private OperandEvaluator evaluator;

    @Autowired
    public DataResolver(OperandEvaluator evaluator) {
        this.evaluator = evaluator;
    }


    public String resolve(String data, Context context, String suite) {
        if (StringUtils.isEmpty(data)) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
        String response = null;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(data);
        List<String> patterns = new ArrayList<>();
        // check all occurrence
        while (matcher.find()) {
            String value = matcher.group(1);
            patterns.add(value);
        }
        
        response = data;

        for (String key : patterns) {
            String val = evaluator.evaluate(key, context, suite);
            response = StringUtils.replace(response, "{{" + key + "}}", val);
        }

        logger.debug("Data [{}] response [{}] suite [{}]", data, response, suite);
        return response;

    }
}
