package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.Policies;
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
 * @since 4/24/2018
 */
@Component(value = "mySQLTimeboundSqlInjectionQueryParamGenerator")
public class MySQLTimeboundSQLInjectionQueryParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "sql_injection_timebound";
    protected static final String PARAM_TYPE = "path_param";
    protected static final String AUTH = "Default"; // BASIC
    protected static final String OPERAND = "200";
    protected static final String INJECTION_DATASET = "@MySQLTimeboundSQLInjections";
    protected static final String DB_NAME = "MySQL";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        String endPoint = path;

        if (! configUtil.isDB(SCENARIO,DB_NAME)){
            return null;
        }
        String dbVersion = configUtil.getDBVersion(SCENARIO,DB_NAME);

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
                    String postFix = PARAM_TYPE + "_" + queryParam.getName() + "_" + DB_NAME  + "_" + configUtil.getTestSuitePostfix(SCENARIO) ;
                    List<TestSuiteMin> testSuites = build(op, path, endPoint, postFix,SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, configUtil.getAssertions(SCENARIO));
                    for (TestSuiteMin testSuite : testSuites) {
                        testSuite.setEndpoint(path + "?" + queryParam.getName() + "=" + "{{"+INJECTION_DATASET+"}}");
                    }
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;
    }
}
