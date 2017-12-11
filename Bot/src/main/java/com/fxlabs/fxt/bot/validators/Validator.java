package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.AssertionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Validator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    abstract void validate(String operand1, String operand2, AssertionContext context, String assertion);

    protected void validationFailed(String operand1, String operand2, AssertionContext context, String assertion) {
        context.setResult("fail");
        logger.info(String.format("Assertion [{}] failed, expected value [{}] but found [{}]", assertion, operand1, operand2));
        context.getLogs().append(String.format("Assertion [%s] failed, expected [%s] but found [%s]", assertion, operand1, operand2));
    }
}
