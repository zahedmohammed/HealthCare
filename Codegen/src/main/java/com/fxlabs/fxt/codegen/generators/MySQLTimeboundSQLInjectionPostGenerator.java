package com.fxlabs.fxt.codegen.generators;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fxlabs.fxt.codegen.generators.base.AbstractGenerator;
import com.fxlabs.fxt.dto.project.Policies;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import com.fxlabs.fxt.dto.project.TestSuiteType;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(value = "mySQLTimeboundSqlInjectionPostGenerator")
public class MySQLTimeboundSQLInjectionPostGenerator extends AbstractGenerator {

    protected static final String SCENARIO = "sql_injection_timebound";
    protected static final String PARAM_TYPE = "post";
    protected static final String AUTH = "Default";// BASIC
    protected static final String INJECTION_DATASET = "@MySQLTimeboundSQLInjections";
    protected static final String DB_NAME = "MySQL";


    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {

        String endPoint = path;

        // Only POST
        if (method != io.swagger.models.HttpMethod.POST) {
            return null;
        }

        if (! configUtil.isDB(SCENARIO,DB_NAME)){
            return null;
        }
        String dbVersion = configUtil.getDBVersion(SCENARIO,DB_NAME);

        Policies policies =  new Policies();
        policies.setRepeatModule(INJECTION_DATASET);

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

        String postFix =  configUtil.getTestSuitePostfix(SCENARIO); //PARAM_TYPE + "_" +
        List<TestSuiteMin> list = new ArrayList<>();
        String testcase = null;

        // check for method from config


        String resSampleMappings = configUtil.getResourceSampleMappings(SCENARIO);
        if ( StringUtils.isBlank(resSampleMappings)) {
            return null;
        }else{
            String[] resSampleMappingsArr = resSampleMappings.split(",");
            if (ArrayUtils.isEmpty(resSampleMappingsArr)){
                return null;
            }

            for( String resName : resSampleMappingsArr)
            {
                String sampleBody = configUtil.getResourceSample(SCENARIO, resName);
                if (StringUtils.isBlank(sampleBody)){
                    continue;
                }
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                JsonNode node = null;
                try {

                    String bodyProperties = configUtil.getBodyProperties(SCENARIO); //TODO: get from config requestMapping.getSqlProperties();

                    System.out.println("Body properties..............");
                    if (StringUtils.isBlank(bodyProperties)){
                        // TODO: If no bodyProperties defined, use defaults
                        return null;
                    }

                    String[] sqlProperties = bodyProperties.split(",");

                    System.out.println("splitte. " + sqlProperties.length);

                    int index = 1;
                    if (! ArrayUtils.isEmpty(sqlProperties)) {
                        for (String prop : sqlProperties) {
                            System.out.println("Prop: " + prop);
                            node = mapper.readTree(sampleBody);
                            TreeNode node_ = node.path(prop);
                            if ( node_.isMissingNode() ){
                                System.out.println(prop + " is missing:("  );
                                continue;
                            }
                            // TODO: handle nested objects

                            ((ObjectNode) node).put(prop,"{{" + INJECTION_DATASET + "}}");
                            testcase = mapper.writeValueAsString(node);

                            List<TestSuiteMin> list_ = build(op, path, endPoint, postFix + "_" + prop, SCENARIO, op.getDescription(), TestSuiteType.SUITE, method, TAG, AUTH, policies, configUtil.getAssertions(SCENARIO));
                            buildTestCase(list_.get(0), index++, testcase);
                            list.add(list_.get(0));
                        }
                    }else{
                        System.out.println("No SQL Properties for  " + resName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }



//        ResourceSample resourceSample = configUtil.getResourceSamples (path);




        return list;
    }



}
