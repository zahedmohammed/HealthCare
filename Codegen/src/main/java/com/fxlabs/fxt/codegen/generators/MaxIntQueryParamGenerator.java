package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @author Intesar Shannan Mohammed
 * @since 4/9/2018
 */
@Component(value = "queryParamGeneratorMaxInt")
public class MaxIntQueryParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "DDOS";
    protected static final String PARAM_TYPE = "query_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();

        if (!configUtil.isDDOSSupportedMethod(method)) {
            return allTestSuites;
        }

        for (Parameter param : op.getParameters()) {

            if (!configUtil.isDDOSSupportedParam(param)) {
                continue;
            }

            if (!configUtil.isDDOSSupportedType(param)) {
                continue;
            }

            if (!configUtil.isDDOSSupportedName(param.getName())) {
                continue;
            }

            int biggerNumber = configUtil.getDDOSSupportedValue(param.getName());


            String postFix = PARAM_TYPE + "_" + configUtil.getTestSuitePostfix(SCENARIO) + "_" + param.getName();
            List<TestSuiteMin> testSuites = build(op, path, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
            for (TestSuiteMin testSuite : testSuites) {
                testSuite.setEndpoint(path + "?" + param.getName() + "=" + biggerNumber);
            }
            allTestSuites.addAll(testSuites);
        }
        return allTestSuites;
    }

}
