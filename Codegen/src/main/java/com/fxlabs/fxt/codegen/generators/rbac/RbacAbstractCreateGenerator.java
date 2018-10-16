package com.fxlabs.fxt.codegen.generators.rbac;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component(value = "rbacAbstractCreateGenerator")
public class RbacAbstractCreateGenerator extends AbstractGenerator {

    public static final String POSTFIX = "rbac_create_abstract";
    public static final String SCENARIO = "create";
    protected static final String AUTH = "";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {



        // Only POST
        if (method != io.swagger.models.HttpMethod.POST) {
            return null;
        }

        // Only body Parameter
        Model model = null;
        for (Parameter p : op.getParameters()) {
            if (!(p instanceof BodyParameter)) {
                return null;
            }
            model = ((BodyParameter) p).getSchema();
        }

        if (model == null || StringUtils.isBlank(model.getReference())) {
            return null;
        }

        //System.out.println ("AbstractCreate -> " + path + " " + op.getOperationId());

        String testcase = factory.getValid(model.getReference());

        List<TestSuiteMin> list = build(op, path, POSTFIX, SCENARIO, op.getDescription(), TestSuiteType.ABSTRACT, method, TAG, AUTH, configUtil.getAssertions(SCENARIO));

        // TODO - if Security required

        // buildAssertion(list.get(0), STATUS_CODE_ASSERTION, EQUALS, OPERAND);

        if (!CollectionUtils.isEmpty(list)) {
            buildTestCase(list.get(0), 1, testcase);
        }

        return list;
    }
}
