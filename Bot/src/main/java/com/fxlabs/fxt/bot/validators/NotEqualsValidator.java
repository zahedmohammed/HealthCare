package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.AssertionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("notEqualsValidator")
public class NotEqualsValidator extends Validator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void validate(String operand1, String operand2, AssertionContext context, String assertion) {
        if (!StringUtils.equals(operand1, operand2)) {
            context.setResult("pass");
        } else {
            validationFailed(operand1, operand2, context, assertion);
        }
    }
}
