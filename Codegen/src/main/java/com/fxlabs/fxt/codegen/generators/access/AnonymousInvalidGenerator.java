package com.fxlabs.fxt.codegen.generators.access;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import io.swagger.models.parameters.QueryParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component(value = "anonymousInvalidGenerator")
public class AnonymousInvalidGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "anonymous_invalid";
    protected static final String AUTH = "NONE";
    protected static final String OPERAND = "401";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {



        for (Parameter param : op.getParameters()) {
            if (param instanceof QueryParameter) {
                QueryParameter queryParam = (QueryParameter) param;
                String name = queryParam.getName();
                String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@RandomInteger}}";
                path = path.replaceAll("\\{" + name + "\\}", defaultVal);
            }
            if (param instanceof PathParameter) {
                PathParameter queryParam = (PathParameter) param;
                String name = queryParam.getName();
                String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@RandomInteger}}";
                path = path.replaceAll("\\{"+name+"\\}" , defaultVal);
            }

        }

        String postFix = configUtil.getTestSuitePostfix(SCENARIO);
        List<TestSuiteMin> list = build(op, path, postFix, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);

        // TODO - if Security required

        // TODO buildTestCase(testSuite)
        if (method == io.swagger.models.HttpMethod.POST || method == io.swagger.models.HttpMethod.PUT) {

        }

        return list;

    }
}
