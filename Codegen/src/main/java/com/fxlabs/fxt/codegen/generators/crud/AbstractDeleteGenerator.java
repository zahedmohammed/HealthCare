package com.fxlabs.fxt.codegen.generators.crud;

import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.codegen.generators.base.Generator;
import com.fxlabs.fxt.codegen.generators.utils.NameUtil;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.PathParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component(value = "abstractDeleteGenerator")
public class AbstractDeleteGenerator extends AbstractGenerator {

    protected static final String POSTFIX = "delete_abstract";
    protected static final String AUTH = "";
    protected static final String OPERAND = "200";

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {


        // Only POST
        if (method != HttpMethod.DELETE) {
            return null;
        }

        // Only body Parameter
        String name = null;
        for (Parameter p : op.getParameters()) {
            if (!(p instanceof PathParameter)) {
                return null;
            }
            name = ((PathParameter) p).getName();
        }

        if (StringUtils.isEmpty(name)) {
            return null;
        }

        String base = NameUtil.extractBase(path);
        System.out.println ("Path: " + path);
        String path_ = StringUtils.replace(path, "{" + name + "}", "{{@" + base + "_post_" +AbstractCreateGenerator.POSTFIX + "_Response.id}}");
        System.out.println ("Eval Path: " + path);

        //System.out.println ("AbstractCreate -> " + path + " " + op.getOperationId());

        //String testcase = factory.getValid(model.getReference());

        List<TestSuiteMin> list = build(op, path, POSTFIX, op.getDescription(), TestSuiteType.ABSTRACT, method, TAG, AUTH);

        list.get(0).setEndpoint(path_);

        // TODO - if Security required

        buildAssertion(list.get(0), STATUS_CODE_ASSERTION, EQUALS, OPERAND);

        return list;
    }
}
