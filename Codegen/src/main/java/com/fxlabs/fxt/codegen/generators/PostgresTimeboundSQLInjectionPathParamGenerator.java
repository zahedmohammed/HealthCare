package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.Policies;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/24/2018
 */
@Component(value = "PostgresTimeboundSqlInjectionPathParamGenerator")
public class PostgresTimeboundSQLInjectionPathParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "sql_injection_timebound";
    protected static final String PARAM_TYPE = "path_param";
    protected static final String AUTH = "Default";// BASIC
    protected static final String OPERAND = "200";
    protected static final String INJECTION_DATASET = "@PostgresTimeboundSQLInjections";
    protected static final String DB_NAME = "Postgres";


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
                    String postFix = PARAM_TYPE + "_" + configUtil.getTestSuitePostfix(SCENARIO) + "_" + DB_NAME + "_" + pathParam.getName();
                    List<TestSuiteMin> testSuites = build(op, path, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, false);
                    for (TestSuiteMin testSuite : testSuites) {
                        String _path = path.replace("{" + pathParam.getName() + "}", "{{"+INJECTION_DATASET+"}}");
                        testSuite.setEndpoint(_path);
                    }
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;
    }
}