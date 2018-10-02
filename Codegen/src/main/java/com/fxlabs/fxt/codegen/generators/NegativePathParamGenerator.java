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
 * @since 6/25/2018
 */
@Component(value = "pathParamGeneratorNegative")
public class NegativePathParamGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "negative_number";
    protected static final String PARARM_TYPE  = "path_param";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        List<TestSuiteMin> allTestSuites = new ArrayList<>();
        if (method == io.swagger.models.HttpMethod.GET) {
            boolean first = true;
            String paramSeparator = "?";
            for ( Parameter param : op.getParameters()){
                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    String name = queryParam.getName();
                    String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@RandomInteger}}";
                    if (!first){
                        paramSeparator = "&";
                    }
                    path = path + paramSeparator + queryParam.getName() + "=" + "{{@RandomInteger}}";
                    first =false;
                }

                if (param instanceof PathParameter){
                    PathParameter pathParam = (PathParameter) param;
                    if ("integer".equals(pathParam.getType())){
                        String postFix = PARARM_TYPE + "_" + pathParam.getName() + "_" + configUtil.getTestSuitePostfix(SCENARIO) ;
                        List<TestSuiteMin> testSuites = build(op, path, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);
                        for (TestSuiteMin testSuite : testSuites) {
                            String name = pathParam.getName();
                            String endPoint = testSuite.getEndpoint();
                            endPoint = endPoint.replace("\\{"+name+"\\}" , "-1");
                            testSuite.setEndpoint(path );
                            testSuite.setCategory(TestSuiteCategory.Negative);
                        }
                        allTestSuites.addAll(testSuites);
                    }
                }
            }
        }
        return allTestSuites;

    }
}
