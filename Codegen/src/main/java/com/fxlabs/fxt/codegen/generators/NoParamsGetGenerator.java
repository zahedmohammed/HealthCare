package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 8/9/2018
 */
@Component(value = "noParamsGetGenerator")
public class NoParamsGetGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "no_params";
    protected static final String PARAM_TYPE = "no_params";
    protected static final String AUTH = "Default";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {

            List<Parameter> params = op.getParameters();
            if (!CollectionUtils.isEmpty(params)){
                for (Parameter param : params){
                    if ( param.getRequired()){
                        return null;
                    }
                }
            }
            // There are no parameters or all params are optional

            String postFix = configUtil.getTestSuitePostfix(SCENARIO) ;
            List<TestSuiteMin> testSuites = build(op, path, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, configUtil.getAssertions(SCENARIO));
            for (TestSuiteMin testSuite : testSuites) {
                testSuite.setEndpoint(path );
                System.out.println(testSuite.getName());
            }
            allTestSuites.addAll(testSuites);
        }
        return allTestSuites;

    }
}
