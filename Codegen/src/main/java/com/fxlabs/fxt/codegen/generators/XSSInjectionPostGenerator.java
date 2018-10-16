package com.fxlabs.fxt.codegen.generators;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammed Luqman Shareef
 * @since 7/3/2018
 */
@Component(value = "xssInjectionPostGenerator")
public class XSSInjectionPostGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "XSS_Injection";
    protected static final String AUTH = "Default";
    protected static final String OPERAND = "200";
    protected static final String INJECTION_DATASET = "@XSSSQLInjections";

    protected static String[] propNames = { "desc", "description", "comment", "comments" , "reason", "note", "notes"};

    List<String> propNamesList = Arrays.asList(propNames);

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

        JsonNode node = factory.getValidAsNode(model.getReference());

        if  (! ( node instanceof ObjectNode)){
            return null;
        }

        boolean found = false;
        ObjectNode objNode = (ObjectNode) node;
        Iterator<Map.Entry<String,JsonNode>> fieldsItr = objNode.fields();
        while (fieldsItr.hasNext()){
            Map.Entry<String,JsonNode> entry = fieldsItr.next();
            if (entry.getValue().getNodeType().equals(JsonNodeType.STRING)){
                if (CollectionUtils.containsInstance(propNamesList, entry.getKey())){
                    objNode.put(entry.getKey(),"{{"+INJECTION_DATASET+"}}");
                    found = true;
                }
            }
        }

        if (!found) return null;

        String testcase = objNode.toString();
        String postFix = configUtil.getTestSuitePostfix(SCENARIO);
        List<TestSuiteMin> list = build(op, path, postFix, SCENARIO,op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, configUtil.getAssertions(SCENARIO));
        if (!CollectionUtils.isEmpty(list)) {
            buildTestCase(list.get(0), 1, testcase);
        }

        return list;
    }
}
