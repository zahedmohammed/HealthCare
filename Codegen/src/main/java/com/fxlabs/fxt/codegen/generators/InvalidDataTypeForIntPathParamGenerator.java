package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 * @since 4/11/2018
 */
@Component(value = "invalidDataTypeForIntPathParamGenerator")
public class InvalidDataTypeForIntPathParamGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "path_param_invalid_datatype_for_int";
    protected static final String AUTH = "BASIC";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {

            for (Parameter param : op.getParameters()) {

                if (!(param instanceof PathParameter)) {
                    continue;
                }

                PathParameter pathParam = (PathParameter) param;

                if (!"integer".equals(pathParam.getType())) {
                    continue;
                }

                String postFix = POSTFIX + "_" + pathParam.getName();
                List<TestSuiteMin> testSuites = build(op, path, postFix, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);

                for (TestSuiteMin testSuite : testSuites) {

                    buildAssertion(testSuite, STATUS_CODE_ASSERTION, NOT_EQUALS, OPERAND);
                    String _path = path.replace("{" + pathParam.getName() + "}", RandomStringUtils.randomAlphanumeric(6));
                    testSuite.setEndpoint(_path);

                }
                allTestSuites.addAll(testSuites);
            }
        }
        return allTestSuites;

    }
}