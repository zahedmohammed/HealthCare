package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import com.mifmif.common.regex.Generex;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/24/2018
 */
@Component(value = "logForgingQueryParamGenerator")
public class LogForgingQueryParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "log_forging";
    protected static final String PARAM_TYPE = "query_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<String> patterns = configUtil.getLogForgingPatterns();

        if (CollectionUtils.isEmpty(patterns)) return null;

        Generex generex = new Generex(patterns.get(RandomUtils.nextInt(0,patterns.size()-1)));
        String logForgerStr = generex.random();

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    String postFix = PARAM_TYPE + "_" + queryParam.getName() + "_" + configUtil.getTestSuitePostfix(SCENARIO);
                    List<TestSuiteMin> testSuites = build(op, path, postFix,SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, configUtil.getAssertions(SCENARIO));
                    for (TestSuiteMin testSuite : testSuites) {
                        testSuite.setEndpoint(path + "?" + queryParam.getName() + "=" + logForgerStr );  // "{{@LogForging}}"
                    }
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;
    }


}
