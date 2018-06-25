package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/11/2018
 */
@Component(value = "nullPathParamGenerator")
public class NullPathParamGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "path_param_null";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
                if (param instanceof PathParameter) {

                    if (!param.getRequired()) {
                        continue;
                    }

                    PathParameter pathParam = (PathParameter) param;
                    String postFix = POSTFIX + "_" + pathParam.getName();
                    List<TestSuiteMin> testSuites = build(op, path, postFix, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
                    for (TestSuiteMin testSuite : testSuites) {
                        buildAssertion(testSuite, STATUS_CODE_ASSERTION, NOT_EQUALS, OPERAND);
                        String _path = path.replace("{" + pathParam.getName() + "}", "null");
                        testSuite.setEndpoint(_path);
                        testSuite.setCategory(TestSuiteCategory.Bug);
                        testSuite.setSeverity(TestSuiteSeverity.Major);
                    }
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;

    }
}
