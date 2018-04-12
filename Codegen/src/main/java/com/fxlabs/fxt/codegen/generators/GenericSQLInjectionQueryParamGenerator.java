package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/9/2018
 */
@Component(value = "genericSqlInjectionQueryParamGenerator")
public class GenericSQLInjectionQueryParamGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "query_param_sql_injection";
    protected static final String AUTH = "BASIC";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
//
//                if (!param.getRequired()) {
//                    continue;
//                }
                
                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    String postFix = POSTFIX + "_" + queryParam.getName();

                    String injectionType = "equals_true";
                    List<TestSuiteMin> testSuites =
                            buildTestSuitesWithParamValue(op, path, postFix, method, queryParam.getName(), injectionType);
                    allTestSuites.addAll(testSuites);

                    injectionType = "equals_true_brackets";
                    testSuites =
                            buildTestSuitesWithParamValue(op, path, postFix, method, queryParam.getName(), injectionType);
                    allTestSuites.addAll(testSuites);

                    injectionType = "simple_select";
                    testSuites =
                            buildTestSuitesWithParamValue(op, path, postFix, method, queryParam.getName(), injectionType);
                    allTestSuites.addAll(testSuites);

                    injectionType = "stacked_queries";
                    testSuites =
                            buildTestSuitesWithParamValue(op, path, postFix, method, queryParam.getName(), injectionType);
                    allTestSuites.addAll(testSuites);

                    injectionType = "union_exploitation";
                    testSuites =
                            buildTestSuitesWithParamValue(op, path, postFix, method, queryParam.getName(), injectionType);
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;

    }


    private List<TestSuiteMin> buildTestSuitesWithParamValue(Operation op, String path, String postFix, io.swagger.models.HttpMethod method,  String param, String injectioType){
        List<TestSuiteMin> testSuites = build(op, path, postFix+"_"+injectioType, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
        for (TestSuiteMin testSuite : testSuites) {
            buildAssertion(testSuite, STATUS_CODE_ASSERTION, NOT_EQUALS, OPERAND);
            testSuite.setEndpoint(path + "?" + param + "=" + "{{@Fxlabs/Common/GenericSQLInjections | "+injectioType+"}}" );
        }
        return testSuites;
    }
}
