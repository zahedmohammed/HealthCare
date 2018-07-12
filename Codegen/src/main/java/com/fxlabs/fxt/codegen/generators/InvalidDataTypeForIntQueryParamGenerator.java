package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 * @since 4/11/2018
 */
@Component(value = "invalidDataTypeForIntQueryParamGenerator")
public class InvalidDataTypeForIntQueryParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "invalid_datatype";
    protected static final String PARAM_TYPE = "query_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
                if (!(param instanceof QueryParameter)) {
                    continue;
                }
                QueryParameter queryParam = (QueryParameter) param;
                if (!"integer".equals(queryParam.getType())) {
                    continue;
                }
                String postFix = configUtil.getTestSuitePostfix(SCENARIO) + "_" + queryParam.getName();
                List<TestSuiteMin> testSuites = build(op, path, postFix,SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
                for (TestSuiteMin testSuite : testSuites) {
                    testSuite.setEndpoint(path + "?" + queryParam.getName() + "=" + RandomStringUtils.randomAlphanumeric(6));
                }
                allTestSuites.addAll(testSuites);
            }
        }
        return allTestSuites;

    }
}
