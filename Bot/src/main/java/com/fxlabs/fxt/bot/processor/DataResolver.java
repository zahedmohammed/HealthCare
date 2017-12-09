package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.bot.assertions.AssertionContext;
import com.fxlabs.fxt.bot.validators.OperandEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataResolver {

    final Logger logger = LoggerFactory.getLogger(getClass());
    final static String PATTERN = "\\{\\{(.*?)\\}\\}";

    private OperandEvaluator evaluator;

    @Autowired
    public DataResolver(OperandEvaluator evaluator) {
        this.evaluator = evaluator;
    }


    public String resolve(String data, AssertionContext context) {
        String response = null;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(data);
        List<String> patterns = new ArrayList<>();
        // check all occurrence
        while (matcher.find()) {
            String value = matcher.group(1);
            patterns.add(value);
        }

        if (CollectionUtils.isEmpty(patterns)) {
            response = data;
        }

        for (String key : patterns) {
            String val = evaluator.evaluate(key, context);
            response = StringUtils.replace(data, "{{" + key + "}}", val);
        }

        logger.info("Data [{}] response [{}]", data, response);
        return response;

    }
}
