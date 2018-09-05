package com.fxlabs.fxt.codegen.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.codegen.generators.json.JSONFactory;
import com.fxlabs.fxt.codegen.generators.utils.AutoCodeConfigUtil;
import com.fxlabs.fxt.dto.project.RequestMapping;
import com.fxlabs.fxt.dto.project.ResourceSample;
import com.fxlabs.fxt.dto.project.TestSuiteAddToVCRequest;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import io.swagger.models.*;
import io.swagger.models.auth.AuthorizationValue;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.*;
import io.swagger.parser.SwaggerCompatConverter;
import io.swagger.parser.SwaggerParser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@Component
public class StubGenerator {

    @Autowired
    private StubHandler stubHandler;

    @Autowired
    private JSONFactory factory;

    @Autowired
    private AutoCodeConfigUtil autoCodeConfigUtil;

    private static final String DEFAULT_OPENAPISPEC_FILE = "OpenAPISpec.yaml";
    private static final String AUTO_CODE_CONFIG_FILE = "AutoCodeConfig.yaml";
    private static final String AUTO_CODE_CONFIG_FILE_URL = "https://raw.githubusercontent.com/fxlabsinc/FX-Sample/master/AutoCodeConfig.yaml";
    private static final String FX_FILE = "Fxfile.yaml";
    private static final String FX_FILE_URL = "https://raw.githubusercontent.com/fxlabsinc/FX-Sample/master/Fxfile.yaml";
    private static final String RESOURCE_SAMPLES_FILE = "ResourceSamples.json";
    public static final String FILE_CONTENT = "FILE_CONTENT";

    /**
     * Checks FXfile.yaml and AutoCodeConfig exists if not creates one.
     *
     * @param projectDir
     */
    public void setupFXConfig(String projectDir, com.fxlabs.fxt.dto.project.AutoCodeConfigMinimal autoCodeConfigContent) {
        try {
            //disable Fxfile creation as part of UX Fix
//            File fxfile = FileUtils.getFile(new File(projectDir), FX_FILE);
//            if (fxfile == null || !fxfile.exists()) {
//                try {
//                    FileUtils.copyURLToFile(new URL(FX_FILE_URL), fxfile);
//                    System.out.println(String.format("%s created successfully...", FX_FILE));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    System.err.println(e.getLocalizedMessage());
//                }
//            }

            File autoCodeConfig = FileUtils.getFile(new File(projectDir), AUTO_CODE_CONFIG_FILE);

            if(autoCodeConfigContent != null){
                ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
                yamlMapper.enable(SerializationFeature.INDENT_OUTPUT);
                yamlMapper.writerWithDefaultPrettyPrinter().writeValue(autoCodeConfig, autoCodeConfigContent);
                CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, autoCodeConfig.getName(), "Written");
            }


//            if (autoCodeConfig == null || !autoCodeConfig.exists() && autoCodeConfigContent == null) {
//                try {
//                    FileUtils.copyURLToFile(new URL(AUTO_CODE_CONFIG_FILE_URL), autoCodeConfig);
//                    System.out.println(String.format("%s created successfully...", AUTO_CODE_CONFIG_FILE));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    System.err.println(e.getLocalizedMessage());
//                }
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Map<String, Integer> generate(String projectDir, String openAPISpec, String headerKey, String headerVal) {

        Map<String, Integer> pathTSCount = new HashMap<>();
        try {

            Swagger swagger = build(projectDir, openAPISpec, headerKey, headerVal);

            AutoCodeConfig config = AutoCodeConfigLoader.loadConfig(projectDir);
            autoCodeConfigUtil.resetConfig();
            autoCodeConfigUtil.setConfig(config);

            factory.init(swagger);

            //String swaggerString = Json.pretty(swagger);

            //System.out.println(swaggerString);

            //System.out.println("---- consumes ----");
            //System.out.println (swagger.getConsumes());

            //System.out.println("---- def ----");
            //System.out.println (swagger.getDefinitions());
            if (swagger.getDefinitions() != null) {
                for (String p : swagger.getDefinitions().keySet()) {
                    Model m = swagger.getDefinitions().get(p);
                    //System.out.println(p + " -> ");
                /*for (String prop : m.getProperties().keySet()) {
                    Property property = m.getProperties().get(prop);
                    System.out.println ("  Key: " + prop);
                    System.out.println ("  Name: " + property.getName());
                    System.out.println ("  Title: " + property.getTitle());
                    System.out.println ("  Access: " + property.getAccess());
                    System.out.println ("  Empty: " + property.getAllowEmptyValue());
                    System.out.println ("  ReadOnly: " + property.getReadOnly());
                    System.out.println ("  Required: " + property.getRequired());
                    System.out.println ("  Type: " + property.getType());
                    System.out.println ("  Enums: " + getEnum(property));
                    System.out.println ("  Min: " + getMin(property));
                    System.out.println ("  Max: " + getMax(property));
                    System.out.println (" ----- ");
                }*/
                }
            }

            List<ResourceSample> resourceSamples = new ArrayList<>();
            List<TestSuiteMin> testSuites = new ArrayList<>();

            for (String p : swagger.getPaths().keySet()) {
                Path path = swagger.getPaths().get(p);

                for (HttpMethod m : path.getOperationMap().keySet()) {
                    Operation op = path.getOperationMap().get(m);
                    // If mapping already found in the RequestMappings File, don't overwrite
                    for (Parameter param : op.getParameters()) {
                        if (!(param instanceof BodyParameter)) {
                            continue;
                        }
                        Model model = ((BodyParameter) param).getSchema();
                        if (model != null && org.apache.commons.lang3.StringUtils.isNotBlank(model.getReference())) {

                            String ref = model.getReference();
                            if (org.apache.commons.lang3.StringUtils.isBlank(ref) || ref.lastIndexOf("/") == -1) {
                                continue;
                            }
                            String resourceName = ref.substring(ref.lastIndexOf("/") + 1);
                            if (m == HttpMethod.POST){
                                resourceName += "-Create";
                            }
                            if (m == HttpMethod.PUT){
                                resourceName += "-Update";
                            }
                            ResourceSample resourceSample  = new ResourceSample();
                            ResourceSample resourceSample_ = autoCodeConfigUtil.getResourceSamples(resourceName);

                            if (resourceSample_ != null) {
                                resourceSample = resourceSample_;
                            } else {
                                String body = factory.getValid(ref);
                                resourceSample.setResource(resourceName);
                                resourceSample.setSample(body);
//                                config.getResourceSamples().add(resourceSample);
                            }
                            resourceSamples.add(resourceSample);
                        }
                    }
                }

            }
            //System.out.println("---- paths ----");
            //System.out.println (swagger.getPaths());
            for (String p : swagger.getPaths().keySet()) {
                Path path = swagger.getPaths().get(p);
                /*System.out.println(p + " -> ");
                System.out.println(" " + path.getGet());
                System.out.println(" " + path.getPost());
                System.out.println(" " + path.getPut());
                System.out.println(" " + path.getDelete());*/

                for (HttpMethod m : path.getOperationMap().keySet()) {
                    Operation op = path.getOperationMap().get(m);

                    //System.out.println (p   " "   op.getOperationId());


                    List<TestSuiteMin> tsList = this.stubHandler.handle(p, m, op);
                    int count = 0;
                    if (tsList != null){
                        count = tsList.size();
                    }
                    pathTSCount.put(p,tsList.size());

                    testSuites.addAll(tsList);
                    /*System.out.println (op.getOperationId());
                    System.out.println (op.getConsumes());
                    System.out.println (op.getProduces());
                    for (String r : op.getResponses().keySet()) {
                        Response res = op.getResponses().get(r);
                        System.out.println(res + "  " + res.getDescription());
                    }

                    for (Parameter pm : op.getParameters()) {
                        System.out.println (pm.getIn());
                        System.out.println (pm.getName());
                    }*/
                }

            }

            //for (TestSuite ts : testSuites) {
            //System.out.println(ts);
            //}

            writeToResourceSampleFile(resourceSamples, projectDir);

            printTS(testSuites, projectDir);

            return pathTSCount;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        }
        return pathTSCount;
    }


    public int addTestSuite(String projectDir, List<TestSuiteAddToVCRequest> testSuiteAddToVCRequests) {

        try {

//            List<TestSuiteMin> testSuites = new ArrayList<>();
//
//            if (testSuiteMin != null) {
//                testSuites.add(testSuiteMin);
//            }

           // writeToResourceSampleFile(resourceSamples, projectDir);

            printTSFromControlPlane(testSuiteAddToVCRequests, projectDir);

            return testSuiteAddToVCRequests.size();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        }
        return 0;
    }


    private void writeToResourceSampleFile(List<ResourceSample> sampleRequests, String dir){

        ObjectMapper yamlMapper = new ObjectMapper();
//        yamlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        if (CollectionUtils.isEmpty(sampleRequests)){
            return;
        }

        try {
            File file = new File(dir + "/"+RESOURCE_SAMPLES_FILE);
            yamlMapper.writerWithDefaultPrettyPrinter().writeValue(file, sampleRequests);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void printTS(List<TestSuiteMin> testSuites, String dir) {
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        yamlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        testSuites.stream().forEach(ts -> {

            //System.out.println ("start "  + ts);
            //System.out.println ("Name "  + ts.getName());

            File file = new File(dir + "/test-suites/AutoCode/" + ts.getParent(), ts.getName() + ".yaml");
            //System.out.println (file);
            if (file.exists()) {
                System.out.println(
                        AnsiOutput.toString(AnsiColor.WHITE,
                                String.format("%s [Skipped]", org.apache.commons.lang3.StringUtils.rightPad(ts.getName(), 100))
                                , AnsiColor.DEFAULT)
                );
                CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, file.getName(), "Skipped");
                return;
            }

            try {


                System.out.println(
                        AnsiOutput.toString(AnsiColor.WHITE,
                                String.format("%s [Written]",
                                        org.apache.commons.lang3.StringUtils.rightPad(ts.getName(), 100))
                                , AnsiColor.DEFAULT)
                );


                FileUtils.touch(file);


                yamlMapper.writerWithDefaultPrettyPrinter().writeValue(file, ts);
                //System.out.println ("done");
                CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, file.getName(), "Written");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println("--");
        System.out.println("Total suites written : " + testSuites.size());
        CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, "Total suites written", "" + testSuites.size());
        System.out.println("");

    }



    private void printTSFromControlPlane(List<TestSuiteAddToVCRequest> testSuiteAddToVCRequests, String dir) {
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        yamlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        testSuiteAddToVCRequests.stream().forEach(ts -> {

            //System.out.println ("start "  + ts);
            //System.out.println ("Name "  + ts.getName());

            File file = new File(dir + "/test-suites/" + ts.getTestSuiteMin().getParent(), ts.getTestSuiteMin().getName() + ".yaml");
            //System.out.println (file);
//            if (file.exists()) {
//                System.out.println(
//                        AnsiOutput.toString(AnsiColor.WHITE,
//                                String.format("%s [Skipped]", org.apache.commons.lang3.StringUtils.rightPad(ts.getName(), 100))
//                                , AnsiColor.DEFAULT)
//                );
//                CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, file.getName(), "Skipped");
//                return;
//            }

            try {


                System.out.println(
                        AnsiOutput.toString(AnsiColor.WHITE,
                                String.format("%s [Written]",
                                        org.apache.commons.lang3.StringUtils.rightPad(ts.getTestSuiteMin().getName(), 100))
                                , AnsiColor.DEFAULT)
                );


                FileUtils.touch(file);

                String content = null;

                if (!CollectionUtils.isEmpty(ts.getProps()) && ts.getProps().containsKey(FILE_CONTENT)) {
                    content = ts.getProps().get(FILE_CONTENT);
                }

                FileUtils.writeStringToFile(file, content, "UTF-8");
                //yamlMapper.writerWithDefaultPrettyPrinter().writeValue(file, content);
                //System.out.println ("done");
                CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, file.getName(), "Written");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println("--");
        System.out.println("Total suites written : " + testSuiteAddToVCRequests.size());
        CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, "Total suites written", "" + testSuiteAddToVCRequests.size());
        System.out.println("");

    }

    private List<String> getEnum(Property p) {
        if (p instanceof StringProperty) {
            // enum, min, max
            return ((StringProperty) p).getEnum();
        } else if (p instanceof ArrayProperty) {
            // unique, min, max
        } else if (p instanceof BooleanProperty) {
            // enum, default
        } else if (p instanceof DateProperty) {

        } else if (p instanceof DateTimeProperty) {

        } else if (p instanceof DoubleProperty) {

        } else if (p instanceof EmailProperty) {

        } else if (p instanceof LongProperty) {

        } else if (p instanceof PasswordProperty) {

        } else if (p instanceof UUIDProperty) {

        }
        return null;
    }

    private Integer getMin(Property p) {
        if (p instanceof StringProperty) {
            // enum, min, max
            return ((StringProperty) p).getMinLength();
        } else if (p instanceof ArrayProperty) {
            // unique, min, max
        } else if (p instanceof BooleanProperty) {
            // enum, default
        } else if (p instanceof DateProperty) {

        } else if (p instanceof DateTimeProperty) {

        } else if (p instanceof DoubleProperty) {

        } else if (p instanceof EmailProperty) {
            return ((EmailProperty) p).getMinLength();
        } else if (p instanceof LongProperty) {

        } else if (p instanceof PasswordProperty) {

        } else if (p instanceof UUIDProperty) {

        }
        return null;
    }

    private Integer getMax(Property p) {
        if (p instanceof StringProperty) {
            // enum, min, max
            return ((StringProperty) p).getMaxLength();
        } else if (p instanceof ArrayProperty) {
            // unique, min, max
        } else if (p instanceof BooleanProperty) {
            // enum, default
        } else if (p instanceof DateProperty) {

        } else if (p instanceof DateTimeProperty) {

        } else if (p instanceof DoubleProperty) {

        } else if (p instanceof EmailProperty) {
            return ((EmailProperty) p).getMaxLength();
        } else if (p instanceof LongProperty) {

        } else if (p instanceof PasswordProperty) {

        } else if (p instanceof UUIDProperty) {

        }
        return null;
    }

    private Swagger build(String projectDir, String openAPISpec, String headerKey, String headerVal) throws IOException {
        Swagger swagger = null;

        if (StringUtils.isEmpty(openAPISpec)) {
            openAPISpec = DEFAULT_OPENAPISPEC_FILE;
        }
        if (!openAPISpec.toLowerCase().startsWith("http")) {
            openAPISpec = projectDir + "/" + openAPISpec;
        }

        List<AuthorizationValue> list = new ArrayList<>();
        // or in a single constructor
        if (!StringUtils.isEmpty(headerKey) && !StringUtils.isEmpty(headerVal)) {
            AuthorizationValue apiKey = new AuthorizationValue(headerKey, headerVal, "header");
            list.add(apiKey);

            try {
                swagger = new SwaggerParser().read(openAPISpec, list, true);
            }catch(Exception ex){
                // Couldnt find/parse the openAPISpec
            }

            if (swagger == null) {
                swagger = new SwaggerCompatConverter().read(openAPISpec, list);
            }
        } else {
            try {
                swagger = new SwaggerParser().read(openAPISpec);
            }catch(Exception ex){
                // Couldnt find/parse the openAPISpec
            }
            if (swagger == null) {
                swagger = new SwaggerCompatConverter().read(openAPISpec);
            }
        }
        return swagger;
    }
}
