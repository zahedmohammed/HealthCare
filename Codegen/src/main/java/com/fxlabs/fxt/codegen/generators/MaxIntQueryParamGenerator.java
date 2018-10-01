package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.code.Match;
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

        // TODO
        if (!configUtil.isDDOSSupportedMethod(method)) {
            return allTestSuites;
        }

        Match match = null;

        match = isSkipPath(path, SCENARIO);

        if (match != null) {
            return allTestSuites;
        }

        String endPoint = path;
        Policies policies = null;

        // Fix path param by replacing with a random
        for (Parameter param : op.getParameters()) {
            if (param instanceof PathParameter) {
                PathParameter pathParam = (PathParameter) param;
                String name = pathParam.getName();
                String defaultVal = pathParam.getDefault() != null ? pathParam.getDefault().toString() : "{{@Random}}";
                endPoint = endPoint.replaceAll("\\{" + name + "\\}", defaultVal);
            }
        }


        List<TestSuiteMin> testSuites = null;
        for (Parameter param : op.getParameters()) {


            if (!configUtil.isDDOSSupportedName(param.getName())) {
                continue;
            }

            if (!configUtil.isDDOSSupportedParam(param)) {
                continue;
            }

            /*if (!configUtil.isDDOSSupportedType(param)) {
                continue;
            }*/

            String value = configUtil.getDDOSSupportedValue(param.getName());


            String postFix = PARAM_TYPE + "_" + configUtil.getTestSuitePostfix(SCENARIO) + "_" + param.getName();

            if (testSuites == null) {
                testSuites = build(op, path, endPoint, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies);
                allTestSuites.addAll(testSuites);
                if (testSuites != null && testSuites.size() > 0) {
                    testSuites.get(0).setEndpoint(endPoint + "?" + param.getName() + "=" + value);
                }
            } else {
                if (testSuites != null && testSuites.size() > 0) {
                    testSuites.get(0).setEndpoint(testSuites.get(0).getEndpoint() + "&" + param.getName() + "=" + value);
                }
            }

        }

        return allTestSuites;
    }

}
