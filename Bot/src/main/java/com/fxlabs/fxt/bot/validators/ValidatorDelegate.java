package com.fxlabs.fxt.bot.validators;

import com.fxlabs.fxt.bot.assertions.AssertionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ValidatorDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("equalsValidator")
    protected Validator equalsValidator;

    @Autowired
    @Qualifier("notEqualsValidator")
    protected Validator notEqualsValidator;

    @Autowired
    protected OperandEvaluator evaluator;

    public void validate(String assertion, AssertionContext context) {
        //logger.info("Validating assertion [{}]", assertion);
        String[] tokens = StringUtils.split(assertion, " ");

        //logger.info("tokens [{}]", tokens);
        if (tokens == null || tokens.length != 3) {
            skipAssertion(context, assertion);
            return;
        }

        try {
            final String operand1 = evaluator.evaluate(tokens[0], context);
            final String OPERATOR = tokens[1];
            final String operand2 = evaluator.evaluate(tokens[2], context);

            switch (OPERATOR) {
                case "==":
                    equalsValidator.validate(operand1, operand2, context, assertion);
                    break;
                case "!=":
                    notEqualsValidator.validate(operand1, operand2, context, assertion);
                    break;
                default:
                    skipAssertion(context, assertion);
            }
        } catch (RuntimeException re) {
            logger.warn(re.getLocalizedMessage());
            skipAssertion(context, assertion);
        }
    }

    protected void skipAssertion(AssertionContext context, String assertion) {
        context.setResult("skip");
        logger.info("Invalid assertion [{}]", assertion);
        context.getLogs().append(String.format("Invalid assertion [%s]", assertion));
    }
}
