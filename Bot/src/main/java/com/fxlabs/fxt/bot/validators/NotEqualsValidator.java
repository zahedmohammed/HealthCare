package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.Context;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component("notEqualsValidator")
public class NotEqualsValidator extends Validator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void validate(String operand1, String operand2, Context context, String assertion) {
        if (StringUtils.equalsIgnoreCase(operand2, "EMPTY") && StringUtils.isNotEmpty(operand1)) {
            validationPass(operand1, operand2, context, assertion);
        } else if (!StringUtils.equals(operand1, operand2)) {
            validationPass(operand1, operand2, context, assertion);
        } else {
            validationFailed(operand1, operand2, context, assertion);
        }
    }
}
