package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.Context;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component("equalsIgnoreCaseValidator")
public class EqualsIgnoreCaseValidator extends Validator {

    @Override
    public void validate(String operand1, String operand2, Context context, String assertion) {
        if (StringUtils.equalsIgnoreCase(operand1, operand2)) {
            validationPass(operand1, operand2, context, assertion);
        } else {
            validationFailed(operand1, operand2, context, assertion);
        }
    }
}
