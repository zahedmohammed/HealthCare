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
@Component(value = "mySQLSqlInjectionPathParamGenerator")
public class MySQLSQLInjectionPathParamGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "sql_injection";
    protected static final String PARAM_TYPE = "path_param";
    protected static final String AUTH = "Default";// BASIC
    protected static final String OPERAND = "200";
    protected static final String INJECTION_DATASET = "@MySQLSQLInjections";
    protected static final String DB_NAME = "MySQL";


    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        if (! configUtil.isDB(DB_NAME)){
            return null;
        }
        String dbVersion = configUtil.getDBVersion(DB_NAME);

        Policies policies =  new Policies();
        policies.setRepeatModule(INJECTION_DATASET);

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            boolean first = true;
            String paramSeparator = "?";

            for (Parameter param : op.getParameters()) {
//                if (param instanceof QueryParameter) {
//                    QueryParameter queryParam = (QueryParameter) param;
//                    String name = queryParam.getName();
//                    String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@RandomInteger}}";
//                    if (!first){
//                        paramSeparator = "&";
//                    }
//                    path = path + paramSeparator + queryParam.getName() + "=" + "{{@RandomInteger}}";
//                    first =false;
//                }
                if (param instanceof PathParameter) {
                    PathParameter pathParam = (PathParameter) param;
                    String postFix = PARAM_TYPE + "_" + POSTFIX + "_" + DB_NAME + "_" + pathParam.getName();
                    List<TestSuiteMin> testSuites = build(op, path, postFix, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, false);
                    List<String> assertions = configUtil.getAssertions(POSTFIX);
                    for (TestSuiteMin testSuite : testSuites) {
                        if (!CollectionUtils.isEmpty(assertions)) {
                            addAssertions(testSuite, assertions);
                        }else{
                            buildAssertion(testSuite, STATUS_CODE_ASSERTION, NOT_EQUALS, OPERAND);
                        }

                        String _path = path.replace("{" + pathParam.getName() + "}", "{{"+INJECTION_DATASET+"}}");
                        testSuite.setEndpoint(_path);

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
