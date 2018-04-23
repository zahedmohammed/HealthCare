package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.AssertionLogger;
import com.fxlabs.fxt.bot.assertions.Context;
import com.fxlabs.fxt.bot.processor.DataEvaluator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class ValidatorDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("equalsValidator")
    protected Validator equalsValidator;

    @Autowired
    @Qualifier("equalsIgnoreCaseValidator")
    protected Validator equalsIgnoreCaseValidator;

    @Autowired
    @Qualifier("notEqualsValidator")
    protected Validator notEqualsValidator;

    @Autowired
    @Qualifier("greaterAndEqualsValidator")
    protected Validator greaterAndEqualsValidator;

    @Autowired
    @Qualifier("greaterValidator")
    protected Validator greaterValidator;

    @Autowired
    @Qualifier("lesserAndEqualsValidator")
    protected Validator lesserAndEqualsValidator;

    @Autowired
    @Qualifier("lesserValidator")
    protected Validator lesserValidator;

    @Autowired
    @Qualifier("regexValidator")
    protected Validator regexValidator;

    @Autowired
    @Qualifier("notRegexValidator")
    protected Validator notRegexValidator;

    @Autowired
    @Qualifier("startsWithValidator")
    protected Validator startsWithValidator;

    @Autowired
    @Qualifier("startsWithIgnoreCaseValidator")
    protected Validator startsWithIgnoreCaseValidator;

    @Autowired
    @Qualifier("endsWithValidator")
    protected Validator endsWithValidator;

    @Autowired
    @Qualifier("endsWithIgnoreCaseValidator")
    protected Validator endsWithIgnoreCaseValidator;


    @Autowired
    protected DataEvaluator evaluator;

    public void validate(String assertion, Context context, StringBuilder assertionLogs) {
        //logger.info("Validating assertion [{}]", assertion);
        String[] tokens = StringUtils.split(assertion, " ");

        //context.getLogs().append(AssertionLogger.LogType.ERROR, context.getSuitename(), "Validating " + assertion);
        //logger.info("tokens [{}]", tokens);
        if (tokens == null || tokens.length != 3) {
            skipAssertion(context, assertion);
            return;
        }

        try {
            final String operand1 = evaluator.evaluate(tokens[0], context, StringUtils.EMPTY);
            final String OPERATOR = tokens[1];
            final String operand2 = evaluator.evaluate(tokens[2], context, StringUtils.EMPTY);

            switch (OPERATOR) {
                case "==":
                    equalsValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "==IgnoreCase":
                    equalsIgnoreCaseValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "!=":
                    notEqualsValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case ">":
                    greaterValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case ">=":
                    greaterAndEqualsValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "<":
                    lesserValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "<=":
                    lesserAndEqualsValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "=~":
                    regexValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "!=~":
                    notRegexValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "startsWith":
                    startsWithValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "startsWithIgnoreCase":
                    startsWithIgnoreCaseValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "endsWith":
                    endsWithValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                case "endsWithIgnoreCase":
                    endsWithIgnoreCaseValidator.validate(operand1, operand2, context, assertion, assertionLogs);
                    break;
                default:
                    skipAssertion(context, assertion);
            }
        } catch (RuntimeException re) {
            logger.warn(re.getLocalizedMessage());
            skipAssertion(context, assertion);
        }
    }

    protected void skipAssertion(Context context, String assertion) {
        context.setResult("fail");
        logger.debug("Invalid assertion [{}]", assertion);
        AssertionLogger logger = null;
        if (context.getLogs() != null) {
            context.getLogs().append(AssertionLogger.LogType.ERROR, context.getSuitename(),
                    String.format("Invalid assertion [%s]. e.g. valid [<Operand1> <Operation> <Operand2>] i.e. @Request.name == @Response.name", assertion));
        }
    }
}
