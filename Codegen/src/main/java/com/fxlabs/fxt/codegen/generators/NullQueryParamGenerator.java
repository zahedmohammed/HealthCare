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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/11/2018
 */
@Component(value = "nullQueryParamGenerator")
public class NullQueryParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "null_value";
    protected static final String PARAM_TYPE = "query_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
                if (param instanceof PathParameter) {
                    PathParameter pathParam = (PathParameter) param;
                    String name = pathParam.getName();
                    String defaultVal = pathParam.getDefault() != null ? pathParam.getDefault().toString() : "{{@RandomInteger}}";
                    path = path.replaceAll("\\{"+name+"\\}" , defaultVal);
                }
                if (param instanceof QueryParameter) {

                    if (!param.getRequired()) {
                        continue;
                    }

                    QueryParameter queryParam = (QueryParameter) param;
                    String postFix = PARAM_TYPE + "_" + configUtil.getTestSuitePostfix(SCENARIO) + "_" + queryParam.getName();
                    List<TestSuiteMin> testSuites = build(op, path, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
                    for (TestSuiteMin testSuite : testSuites) {
                        String _path = path.replace("{" + queryParam.getName() + "}", "null");
                        testSuite.setEndpoint(_path);
                    }
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;

    }
}
