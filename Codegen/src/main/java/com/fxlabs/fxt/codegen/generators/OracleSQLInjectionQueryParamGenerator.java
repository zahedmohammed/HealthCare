package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.code.Database;
import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/24/2018
 */
@Component(value = "oracleSqlInjectionQueryParamGenerator")
public class OracleSQLInjectionQueryParamGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "query_param_sql_injection_Oracle";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";
    protected static final String DB_NAME = "Oracle";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        if (! isDB(DB_NAME)){
            return null;
        }
        String dbVersion = getDBVersion(DB_NAME);


        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    String postFix = POSTFIX + "_" + queryParam.getName();
                    List<TestSuiteMin> testSuites = build(op, path, postFix, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
                    for (TestSuiteMin testSuite : testSuites) {
                        buildAssertion(testSuite, STATUS_CODE_ASSERTION, NOT_EQUALS, OPERAND);
                        testSuite.setEndpoint(path + "?" + queryParam.getName() + "=" + "{{@OracleSQLInjections}}");
                        testSuite.setCategory(TestSuiteCategory.Security_SQL_Injection);
                        testSuite.setSeverity(TestSuiteSeverity.Major);
                    }
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;
    }
}
