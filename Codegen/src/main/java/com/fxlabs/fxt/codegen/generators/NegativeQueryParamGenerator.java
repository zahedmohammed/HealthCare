package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.*;
import io.swagger.models.Operation;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;
import io.swagger.models.parameters.Parameter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/9/2018
 */
@Component(value = "queryParamGeneratorNegative")
public class NegativeQueryParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "negative";
    protected static final String PARAM_TYPE = "query_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for ( Parameter param : op.getParameters()){
                if (param instanceof QueryParameter){
                    QueryParameter queryParam = (QueryParameter) param;
                    if ("integer".equals(queryParam.getType())){
                        String postFix = PARAM_TYPE + "_" + configUtil.getTestSuitePostfix(SCENARIO) + "_"  + queryParam.getName() ;
                        List<TestSuiteMin> testSuites = build(op, path, postFix,SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
                        for (TestSuiteMin testSuite : testSuites) {
                            testSuite.setEndpoint(path + "?" + queryParam.getName() + "=" + "-1");
                        }
                        allTestSuites.addAll(testSuites);
                    }
                }
            }
        }
        return allTestSuites;

    }
}
