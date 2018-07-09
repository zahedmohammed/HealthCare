package com.fxlabs.fxt.codegen.generators.access;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component(value = "invalidCredGenerator")
public class InvalidCredGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "auth_invalid";
    protected static final String AUTH = "INVALID_AUTH";
    protected static final String OPERAND = "401";

    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        for ( Parameter param : op.getParameters()) {
//            if (param instanceof QueryParameter) {
//                QueryParameter queryParam = (QueryParameter) param;
//                String name = queryParam.getName();
//                String defaultVal = queryParam.getDefault() != null ? queryParam.getDefault().toString() : "{{@RandomInteger}}";
//                path = path.replaceAll("\\{"+name+"\\}" , defaultVal);
//            }

            if (param instanceof PathParameter) {
                PathParameter pathParam = (PathParameter) param;
                String name = pathParam.getName();
                String defaultVal = pathParam.getDefault() != null ? pathParam.getDefault().toString() : "{{@RandomInteger}}";
                path = path.replaceAll("\\{" + name + "\\}", defaultVal);
            }
        }

        List<TestSuiteMin> list = build(op, path, POSTFIX, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH);

        // TODO - if Security required
        List<String> assertions = configUtil.getAssertions(POSTFIX);
        if (!CollectionUtils.isEmpty(assertions)) {
            addAssertions(list.get(0), assertions);
        }else{
            buildAssertion(list.get(0), STATUS_CODE_ASSERTION, EQUALS, OPERAND);
        }

        // TODO buildTestCase(testSuite)
        if (method == io.swagger.models.HttpMethod.POST || method == io.swagger.models.HttpMethod.PUT) {

        }

        return list;
    }
}
