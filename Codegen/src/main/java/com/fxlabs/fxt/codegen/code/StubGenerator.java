package com.fxlabs.fxt.codegen.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.codegen.generators.json.JSONFactory;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import io.swagger.models.*;
import io.swagger.models.auth.AuthorizationValue;
import io.swagger.models.properties.*;
import io.swagger.parser.SwaggerCompatConverter;
import io.swagger.parser.SwaggerParser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class StubGenerator {

    @Autowired
    private StubHandler stubHandler;

    @Autowired
    private JSONFactory factory;

    public void generate(String spec, String dir, String headerKey, String headerVal) {

        try {

            Swagger swagger = build(spec, headerKey, headerVal);

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

            List<TestSuiteMin> testSuites = new ArrayList<>();

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

                    //System.out.println (p + " " + op.getOperationId());
                    testSuites.addAll(this.stubHandler.handle(p, m, op));
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

            printTS(testSuites, dir);


        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

    private void printTS(List<TestSuiteMin> testSuites, String dir) {
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        yamlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        testSuites.stream().forEach(ts -> {

            //System.out.println ("start "  + ts);
            //System.out.println ("Name "  + ts.getName());

            File file = new File(dir + "/" + ts.getParent(), ts.getName() + ".yaml");
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

    private Swagger build(String spec, String headerKey, String headerVal) throws IOException {
        Swagger swagger = null;

        List<AuthorizationValue> list = new ArrayList<>();
        // or in a single constructor
        if (!StringUtils.isEmpty(headerKey) && !StringUtils.isEmpty(headerVal)) {
            AuthorizationValue apiKey = new AuthorizationValue(headerKey, headerVal, "header");
            list.add(apiKey);

            swagger = new SwaggerParser().read(spec, list, true);

            if (swagger == null) {
                swagger = new SwaggerCompatConverter().read(spec, list);
            }
        } else {

            swagger = new SwaggerParser().read(spec);

            if (swagger == null) {
                swagger = new SwaggerCompatConverter().read(spec);
            }
        }
        return swagger;
    }
}
