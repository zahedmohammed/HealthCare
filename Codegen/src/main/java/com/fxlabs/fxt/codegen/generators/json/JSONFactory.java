package com.fxlabs.fxt.codegen.generators.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fxlabs.fxt.codegen.generators.utils.AutoCodeConfigUtil;
import com.fxlabs.fxt.codegen.generators.utils.JsonFormatUtil;
import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.properties.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class JSONFactory {

    @Autowired
    private AutoCodeConfigUtil autoCodeConfigUtil;

    private ThreadLocal<Map<String, ObjectNode>> nodes = new InheritableThreadLocal<>();

    public void init(Swagger swagger) {

        nodes.set(new HashMap<>());

        Map<String, Model> m = new HashMap<>();
        Map<String, JsonNode> jn = new HashMap<>();

        if (swagger.getDefinitions() != null) {
            for (String key : swagger.getDefinitions().keySet()) {
                Model model = swagger.getDefinitions().get(key);
                m.put(key, model);
            }
        }

        initialize(m);

    }

    public ObjectNode getValidAsNode(String schema) {
        schema = extractSchema(schema);
        return nodes.get().get(schema);
    }

    public String getValid(String schema) {
        schema = extractSchema(schema);
        //System.out.println ("Schema : " + schema);
        String json = nodes.get().get(schema).toString();

        return JsonFormatUtil.replaceQuotes(json);
    }

    private String extractSchema(String schema) {
        if (StringUtils.isEmpty(schema)) {
            return schema;
        }
        String[] tokens = schema.split("/");
        return tokens[tokens.length - 1];
    }


    private void initialize(Map<String, Model> map) {
        //Map<String, ObjectNode> nodes = new HashMap<>();

        for (String key : map.keySet()) {

            //System.out.println ("Key : " + key);

            Model m = map.get(key);

            //System.out.println(key);


            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();

            nodes.get().put(key, node);

            buildNode(key, node, m.getProperties(), map);

        }

        //return nodes;
    }

    /*private String[] skippedProps = {
            "id",
            "createdBy", "created_by",
            "createdDate", "created_date",
            "lastModifiedBy", "last_modified_by",
            "modifiedBy", "modified_by",
            "lastModifiedDate", "last_modified_date",
            "modifiedDate", "modified_date",
            "version"
    };

    List<String> skip = Arrays.asList(skippedProps);*/

    private void buildNode(String nodeName, ObjectNode node, Map<String, Property> m, Map<String, Model> map) {

        if (node == null || m == null || map == null) {
//            System.out.println("m is null");
            return;
        }

        for (String p : m.keySet()) {

            //System.out.println(p + "...");

            Property prop = m.get(p);

            // skip properties
            /*if (CollectionUtils.containsInstance(skip, p) && !BooleanUtils.isTrue(prop.getRequired())) {
                continue;
            }*/

            if (prop instanceof ArrayProperty) {
                // TODO ( (ArrayProperty) prop).g
                ArrayNode arrayNode = new ObjectMapper().createArrayNode();
                node.set(p, arrayNode);
            } else if (prop instanceof BooleanProperty) {
                Boolean val = null;
                String _val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (StringUtils.isNotEmpty(_val)) {
                    val = Boolean.parseBoolean(_val);
                } else if (BooleanUtils.isTrue(prop.getRequired())) {
                    val = Boolean.FALSE;
                }
                node.put(p, val);
            } else if (prop instanceof DateProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (val == null && BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@Date}}";
                }
                node.put(p, val);
            } else if (prop instanceof DateTimeProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (val == null && BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@DateTime}}";
                }
                node.put(p, val);
            } else if (prop instanceof DoubleProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (val == null && BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@RandomDouble | 4}}";
                }
                node.put(p, val);
            } else if (prop instanceof EmailProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (val == null) {
                    val = "{{@Faker.internet.emailAddress}}";
                }
                node.put(p, val);
            } else if (prop instanceof PasswordProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (val == null && BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@Password | 12}}";
                }
                node.put(p, val);
            } else if (prop instanceof StringProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                System.out.println (" String " + nodeName + " " + p + " " + val);
                if (val == null) {
                    List<String> enums = ((StringProperty) prop).getEnum();
                    if (!CollectionUtils.isEmpty(enums)) {
                        val = enums.get(RandomUtils.nextInt(0, enums.size()));
                    }
                }
                else if (val == null && BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@Random}}";
                }
                node.put(p, val);
            } else if (prop instanceof UUIDProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (val == null && BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@RandomUUID}}";
                }
                node.put(p, val);
            } else if (prop instanceof IntegerProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (val == null && BooleanUtils.isTrue(prop.getRequired())) {
                    val = "{{@RandomInteger}}";
                }
                node.put(p, val);
            } else if (prop instanceof LongProperty) {
                String val = null;
                val = this.autoCodeConfigUtil.getPropertyMapping(nodeName, p);
                if (val == null && BooleanUtils.isTrue(prop.getRequired())) {
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

                ObjectNode n = new ObjectMapper().createObjectNode();
                node.set(p, n);

                String[] tokens = ref.split("/");
                String type = tokens[tokens.length - 1];

                Model m_ = map.get(type);
                if (m_ != null) {
                    buildNode(p, n, m_.getProperties(), map);
                } else {
                    System.err.println(String.format("Invalid Ref [%s], eval [%s] ", type, ref));
                }

            } else {
                node.putNull(p);
            }
        }
    }
}
