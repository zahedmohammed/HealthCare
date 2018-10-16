package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.Policies;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
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
 * @since 4/9/2018
 */
@Component(value = "queryParamGeneratorEmpty")
public class EmptyQueryParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "empty_value";
    protected static final String PARAM_TYPE = "query_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        String endPoint = path;
        Policies policies = null;
        List<TestSuiteMin> allTestSuites = new ArrayList<>();

        for (Parameter param : op.getParameters()) {

            if (param instanceof PathParameter) {
                PathParameter pathParam = (PathParameter) param;
                String name = pathParam.getName();
                String defaultVal = pathParam.getDefault() != null ? pathParam.getDefault().toString() : "{{@Random}}";
                endPoint = endPoint.replaceAll("\\{"+name+"\\}" , defaultVal);
            }
        }
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {

                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    String postFix = PARAM_TYPE + "_" + queryParam.getName() + "_" + configUtil.getTestSuitePostfix(SCENARIO) ;
                    List<TestSuiteMin> testSuites = build(op, path, endPoint, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, configUtil.getAssertions(SCENARIO));
                    for (TestSuiteMin testSuite : testSuites) {
                        testSuite.setEndpoint(endPoint + "?" + queryParam.getName() + "=" + "");
                    }
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;

    }
}
