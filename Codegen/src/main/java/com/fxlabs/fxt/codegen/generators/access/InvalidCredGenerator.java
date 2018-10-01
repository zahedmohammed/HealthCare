package com.fxlabs.fxt.codegen.generators.access;

import com.fxlabs.fxt.codegen.code.Generator;
import com.fxlabs.fxt.codegen.code.Match;
import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.Policies;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component(value = "invalidCredGenerator")
public class InvalidCredGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "auth_invalid";
    protected static final String AUTH = "Invalid_Auth";


    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        // Security should kick-in before post body validation
        /*if (method == io.swagger.models.HttpMethod.POST || method == io.swagger.models.HttpMethod.PUT) {
            return null;
        }*/

        Generator generator = configUtil.get(SCENARIO);
        if (generator == null || generator.isInactive()) {
            return null;
        }

        String endPoint = path;

        List<TestSuiteMin> list = null;

        //  Non-Secured Paths

        Match match = null;

        match = isSkipPath(path, SCENARIO);

        if (match == null) {
            System.out.println(String.format("No Invalid-Secured matches found for path [%s]...", path));

            for (Parameter param : op.getParameters()) {
                if (param instanceof QueryParameter) {
                    QueryParameter queryParam = (QueryParameter) param;
                    String name = queryParam.getName();
                    String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@RandomInteger}}";
                    endPoint = endPoint.replaceAll("\\{" + name + "\\}", defaultVal);
                }
                if (param instanceof PathParameter) {
                    PathParameter queryParam = (PathParameter) param;
                    String name = queryParam.getName();
                    String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@Random}}";
                    endPoint = endPoint.replaceAll("\\{" + name + "\\}", defaultVal);
                }
            }

            Policies policies = null;

            String postFix = configUtil.getTestSuitePostfix(SCENARIO);
            list = build(op, path, endPoint, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies);


        }

        return list;
    }
}
