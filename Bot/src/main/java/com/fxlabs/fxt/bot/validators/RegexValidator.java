package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.Context;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component("regexValidator")
public class RegexValidator extends Validator {

    @Override
    public void validate(String operand1, String operand2, Context context, String assertion) {
        if (operand1.matches(operand2)) {
            validationPass(operand1, operand2, context, assertion);
        } else {
            validationFailed(operand1, operand2, context, assertion);
        }
    }
}
