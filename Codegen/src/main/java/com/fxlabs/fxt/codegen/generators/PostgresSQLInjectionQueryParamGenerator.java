package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/24/2018
 */
@Component(value = "postgresSqlInjectionQueryParamGenerator")
public class PostgresSQLInjectionQueryParamGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "sql_injection";
    protected static final String PARAM_TYPE = "path_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";
    protected static final String DB_NAME = "Postgres";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        if (! configUtil.isDB(DB_NAME)){
            return null;
        }
        String dbVersion = configUtil.getDBVersion(DB_NAME);

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    String postFix = PARAM_TYPE + "_" + POSTFIX + "_" + DB_NAME + "_" + queryParam.getName();
                    List<TestSuiteMin> testSuites = build(op, path, postFix, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
                    List<String> assertions = configUtil.getAssertions(POSTFIX);
                    for (TestSuiteMin testSuite : testSuites) {
                        if (!CollectionUtils.isEmpty(assertions)) {
                            addAssertions(testSuite, assertions);
                        }else{
                            buildAssertion(testSuite, STATUS_CODE_ASSERTION, NOT_EQUALS, OPERAND);
                        }
                        testSuite.setEndpoint(path + "?" + queryParam.getName() + "=" + "{{@PostgresSQLInjections}}");
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
