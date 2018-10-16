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
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 8/9/2018
 */
@Component(value = "slaGetGenerator")
public class SLAGetGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "sla";
    protected static final String PARAM_TYPE = "query_param";
    protected static final String AUTH = "Default";
    protected static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();

        // TODO
        if (method != io.swagger.models.HttpMethod.GET) {
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
        // TODO replace with name
        for (Parameter param : op.getParameters()) {
            if (param instanceof PathParameter) {
                PathParameter pathParam = (PathParameter) param;
                String name = pathParam.getName();
                String defaultVal = pathParam.getDefault() != null ? pathParam.getDefault().toString() : "{{@Random}}";
                endPoint = endPoint.replaceAll("\\{" + name + "\\}", defaultVal);
            }
        }

        List<TestSuiteMin> testSuites = null;

        // check full mapping exists for the path
        String val = configUtil.getDDOSMappedValue(path, SCENARIO);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(val)) {
            // postFix -> filename
            // val -> endpoint
            String postFix = PARAM_TYPE + "_" + configUtil.getTestSuitePostfix(SCENARIO) + "_Mapped";
            testSuites = build(op, path, val, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, configUtil.getAssertions(SCENARIO));
            allTestSuites.addAll(testSuites);
        } else if (!CollectionUtils.isEmpty(op.getParameters())) {

            for (Parameter param : op.getParameters()) {

                if (!configUtil.isDDOSSupportedName(param.getName(), SCENARIO)) {
                    continue;
                }

                if (!configUtil.isDDOSSupportedParam(param)) {
                    continue;
                }

                /*if (!configUtil.isDDOSSupportedType(param)) {
                    continue;
                }*/

                String value = configUtil.getDDOSSupportedValue(param.getName(), SCENARIO);


                String postFix = PARAM_TYPE + "_" + param.getName() + "_" + configUtil.getTestSuitePostfix(SCENARIO);

                if (testSuites == null) {
                    testSuites = build(op, path, endPoint, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, configUtil.getAssertions(SCENARIO));
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
        } else {
            String postFix = PARAM_TYPE + "_" + configUtil.getTestSuitePostfix(SCENARIO) + "_Mapped";
            testSuites = build(op, path, endPoint, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, configUtil.getAssertions(SCENARIO));
            allTestSuites.addAll(testSuites);
        }

        return allTestSuites;
    }
}
