package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 * @since 4/11/2018
 */
@Component(value = "getEmptyStringPathParamGenerator")
public class EmptyStringPathParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "empty_value";
    protected static final String PARAM_TYPE = "path_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        return null;

//        List<TestSuiteMin> allTestSuites = new ArrayList<>();
//        if (method == io.swagger.models.HttpMethod.GET) {
//
//            for (Parameter param : op.getParameters()) {
//                if (!(param instanceof PathParameter)) {
//                    continue;
//                }
//                PathParameter pathParam = (PathParameter) param;
//                if (!"string".equals(pathParam.getType()) || !pathParam.getRequired()) {
//                    continue;
//                }
//                String postFix = PARAM_TYPE + "_" + configUtil.getTestSuitePostfix(SCENARIO) + "_" + pathParam.getName();
//                List<TestSuiteMin> testSuites = build(op, path, postFix,SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
//
//                for (TestSuiteMin testSuite : testSuites) {
//                    String _path = path.replace("{" + pathParam.getName() + "}", "");
//                    testSuite.setEndpoint(_path);
//                }
//                allTestSuites.addAll(testSuites);
//            }
//        }
//        return allTestSuites;

    }
}
