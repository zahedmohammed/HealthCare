package com.fxlabs.fxt.codegen.generators.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.properties.*;
import org.apache.commons.lang3.BooleanUtils;

import java.util.HashMap;
import java.util.Map;

public class JSONFactory {

    private ThreadLocal<Map<String, Model>> models = new InheritableThreadLocal<>();

    public void init(Swagger swagger) {

        Map<String, Model> m = new HashMap<>();
        Map<String, JsonNode> jn = new HashMap<>();

        for (String key : swagger.getDefinitions().keySet()) {
            Model model = swagger.getDefinitions().get(key);
            m.put(key, model);
        }

        initialize(m);

    }

    private Map<String, ObjectNode> initialize(Map<String, Model> map) {
        Map<String, ObjectNode> nodes = new HashMap<>();

        for (String key : map.keySet()) {

            Model m = map.get(key);


            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();

            nodes.put(key, node);

            buildNode(node, m.getProperties(), map);


        }

        return nodes;
    }

    private void buildNode(ObjectNode node, Map<String, Property> m, Map<String, Model> map) {

        for (String p : m.keySet()) {

            Property prop = m.get(p);

            if (prop instanceof ArrayProperty) {
                // TODO ( (ArrayProperty) prop).g
                node.set(p, new ObjectMapper().createArrayNode());
            } else if (prop instanceof BooleanProperty) {
                Boolean val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = Boolean.FALSE;
                }
                node.put(p, val);
            } else if (prop instanceof DateProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@Date}}";
                }
                node.put(p, val);
            } else if (prop instanceof DateTimeProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@DateTime}}";
                }
                node.put(p, val);
            } else if (prop instanceof DoubleProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@RandomDecimal | 4}}";
                }
                node.put(p, val);
            } else if (prop instanceof EmailProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@Random | 8}}@test.local";
                }
                node.put(p, val);
            } else if (prop instanceof PasswordProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@Password | 8}}";
                }
                node.put(p, val);
            } else if (prop instanceof StringProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@Random | 8}}";
                }
                node.put(p, val);
            } else if (prop instanceof UUIDProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@RandomUUID}}";
                }
                node.put(p, val);
            } else if (prop instanceof IntegerProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@RandomInteger}}";
                }
                node.put(p, val);
            } else if (prop instanceof LongProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@RandomLong}}";
                }
                node.put(p, val);
            } else if (prop instanceof MapProperty) {
                String val = null;
                if (BooleanUtils.isTrue(prop.getRequired())) {
                    // TODO
                }
                node.put(p, val);
            } else if (prop instanceof ObjectProperty) {
                // TODO
                node.putNull(p);

            } else if (prop instanceof RefProperty) {
                String ref = ((RefProperty) prop).get$ref();
                //System.out.println("Ref " + ref);

                ObjectNode n = new ObjectMapper().createObjectNode();
                node.set(p, n);

                String[] tokens = ref.split("/");
                String type = tokens[tokens.length - 1];

                Model m_ = map.get(type);
                if (m_ != null) {
                    buildNode(n, m_.getProperties(), map);
                } else {
                    System.err.println(String.format("Invalid Ref [%s], eval [%s] ", type, ref));
                }


            } else {
                node.putNull(p);
            }
        }
    }
}
