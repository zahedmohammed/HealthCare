package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import org.springframework.stereotype.Component;

@Component(value = "anonymousInvalidGenerator")
public class AnonymousInvalidGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "anonymous_invalid";
    protected static final String AUTH = "NONE";
    protected static final String OPERAND = "401";

    @Override
    public TestSuiteMin generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        TestSuiteMin testSuite = build(op, path, POSTFIX, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);

        // TODO - if Security required
        buildAssertion(testSuite, STATUS_CODE_ASSERTION, EQUALS, OPERAND);

        // TODO buildTestCase(testSuite)
        if (method == io.swagger.models.HttpMethod.POST || method == io.swagger.models.HttpMethod.PUT) {

        }

        return testSuite;

    }
}
