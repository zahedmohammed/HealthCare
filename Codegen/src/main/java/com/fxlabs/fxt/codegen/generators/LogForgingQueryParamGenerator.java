package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import com.mifmif.common.regex.Generex;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
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

    protected static final String POSTFIX = "query_param_log_forging";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        if (this.getAutoCodeConfig() == null ) return null;

        List<String> patterns = this.getAutoCodeConfig().getLogForgingPatterns();

        if (CollectionUtils.isEmpty(patterns)) return null;
//
//        Generex generex1 = new Generex("[6-9]\\d{9}");
//        String logForgerStr1 = generex1.random();
//        System.out.println(logForgerStr1);

        Generex generex = new Generex(patterns.get(0));
        String logForgerStr = generex.random();
        System.out.println(logForgerStr);


        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            for (Parameter param : op.getParameters()) {
                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    System.out.println("QP :" + queryParam.getName());
                    String postFix = POSTFIX + "_" + queryParam.getName();
                    List<TestSuiteMin> testSuites = build(op, path, postFix, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
                    for (TestSuiteMin testSuite : testSuites) {
                        buildAssertion(testSuite, STATUS_CODE_ASSERTION, NOT_EQUALS, OPERAND);
                        testSuite.setEndpoint(path + "?" + queryParam.getName() + "=" + logForgerStr );  // "{{@LogForging}}"
                        testSuite.setCategory(TestSuiteCategory.Security_DDOS);
                        testSuite.setSeverity(TestSuiteSeverity.Major);
                    }
                    allTestSuites.addAll(testSuites);
                }
            }
        }
        return allTestSuites;
    }
}
