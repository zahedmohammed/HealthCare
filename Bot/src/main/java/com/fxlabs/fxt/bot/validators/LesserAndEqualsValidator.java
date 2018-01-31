package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.Context;
import org.springframework.stereotype.Component;

@Component("lesserAndEqualsValidator")
public class LesserAndEqualsValidator extends Validator {

    @Override
    public void validate(String operand1, String operand2, Context context, String assertion) {
        try {
            Double op1 = Double.parseDouble(operand1);
            Double op2 = Double.parseDouble(operand2);

            if (op1 <= op2) {
                context.setResult("pass");
            } else {
                validationFailed(operand1, operand2, context, assertion);
            }
        } catch (RuntimeException re) {
            logger.warn(re.getLocalizedMessage());
            validationFailed(operand1, operand2, context, assertion);

        }
    }
}