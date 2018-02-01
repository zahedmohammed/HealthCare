package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.AssertionLogger;
import com.fxlabs.fxt.bot.assertions.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Intesar Shannan Mohammed
 */
public abstract class Validator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    abstract void validate(String operand1, String operand2, Context context, String assertion);

    protected void validationFailed(String operand1, String operand2, Context context, String assertion) {
        context.setResult("fail");
        logger.info(String.format("Assertion [{}] failed, expected value [{}] but found [{}]", assertion, operand2, operand1));
        context.getLogs().append(AssertionLogger.LogType.ERROR, context.getSuitename(),
                String.format("Assertion [%s] failed, expected [%s] but found [%s]", assertion, operand2, operand1));
    }
}
