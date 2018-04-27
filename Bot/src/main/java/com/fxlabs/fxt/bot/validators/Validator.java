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

    abstract void validate(String operand1, String operand2, Context context, String assertion, StringBuilder assertionLogs);

    protected void validationPass(String operand1, String operand2, Context context, String assertion, StringBuilder assertionLogs) {
        context.setResult("pass");
        String msg = String.format("Assertion [%s] passed, expected [%s] and found [%s]", assertion, operand2, operand1);
        //logger.debug(msg);
        context.getLogs().append(AssertionLogger.LogType.INFO, context.getSuitename(), msg);
        assertionLogs.append(msg);
    }

    protected void validationFailed(String operand1, String operand2, Context context, String assertion, StringBuilder assertionLogs) {
        context.setResult("fail");
        String msg = String.format("Assertion [%s] failed, expected value [%s] but found [%s]", assertion, operand2, operand1);
        //logger.debug(msg);
        context.getLogs().append(AssertionLogger.LogType.ERROR, context.getSuitename(), msg);
        assertionLogs.append(msg);
    }
}
