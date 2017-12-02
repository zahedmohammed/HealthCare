package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.AssertionContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component("endsWithValidator")
public class EndsWithValidator extends Validator {

    @Override
    public void validate(String operand1, String operand2, AssertionContext context, String assertion) {
        if (StringUtils.endsWith(operand1, operand2)) {
            context.setResult("pass");
        } else {
            validationFailed(operand1, operand2, context, assertion);
        }
    }
}
