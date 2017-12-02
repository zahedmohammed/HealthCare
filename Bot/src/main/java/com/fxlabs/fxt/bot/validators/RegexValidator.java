package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.AssertionContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component("regexValidator")
public class RegexValidator extends Validator {

    @Override
    public void validate(String operand1, String operand2, AssertionContext context, String assertion) {
        if (operand1.matches(operand2)) {
            context.setResult("pass");
        } else {
            validationFailed(operand1, operand2, context, assertion);
        }
    }
}
