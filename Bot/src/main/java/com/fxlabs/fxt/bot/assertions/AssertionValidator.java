package com.fxlabs.fxt.bot.assertions;

import com.fxlabs.fxt.bot.validators.ValidatorDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssertionValidator {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ValidatorDelegate delegate;

    public void validate(List<String> assertions, AssertionContext context) {
        assertions.forEach(assertion -> {
            delegate.validate(assertion, context);
        });
    }
}
