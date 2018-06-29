package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.code.Database;
import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.*;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
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
@Component(value = "mySQLSqlInjectionQueryParamGenerator")
public class MySQLSQLInjectionQueryParamGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "query_param_sql_injection_MySQL";
    protected static final String AUTH = "Default"; // BASIC
    protected static final String OPERAND = "200";
    protected static final String INJECTION_DATASET = "@MySQLSQLInjections";
    protected static final String DB_NAME = "MySQL";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        if (! isDB(DB_NAME)){
            return null;
        }
        String dbVersion = getDBVersion(DB_NAME);

        Policies policies =  new Policies();
        policies.setRepeatModule(INJECTION_DATASET);

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
//                if (param instanceof PathParameter) {
//                    PathParameter pathParam = (PathParameter) param;
//                    String name = pathParam.getName();
//                    String defaultVal = pathParam.getDefault() != null ? pathParam.getDefault().toString() : "{{@RandomInteger}}";
//                    path = path.replaceAll("\\{"+name+"\\}" , defaultVal);
//                    System.out.println("Path:: " + path);
//                }
                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    String postFix = POSTFIX + "_" + queryParam.getName();
                    List<TestSuiteMin> testSuites = build(op, path, postFix, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, false);
                    for (TestSuiteMin testSuite : testSuites) {
                        buildAssertion(testSuite, STATUS_CODE_ASSERTION, NOT_EQUALS, OPERAND);
                        testSuite.setEndpoint(path + "?" + queryParam.getName() + "=" + "{{"+INJECTION_DATASET+"}}");
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
