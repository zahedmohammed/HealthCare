package com.fxlabs.fxt.codegen.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.dto.project.ResourceSample;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 */
public class AutoCodeConfigLoader {

    protected static Logger logger = LoggerFactory.getLogger(AutoCodeConfigLoader.class);
    protected static final String RESOURCE_SAMPLES_FILE = "ResourceSamples.json";

    public static AutoCodeConfig loadConfig(String projectDir) throws Exception {
        AutoCodeConfig config = null;

        if (projectDir == null) {
            return null;
        }
        try {

            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

            System.out.println("loading AutoCodeConfig.yaml...");
            CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, "AutoCodeConfig.yaml", "Loading");
            if (!StringUtils.endsWithIgnoreCase(projectDir, "/")) {
                projectDir += "/";
            }
            File configFile = FileUtils.getFile(new File(projectDir), "AutoCodeConfig.yaml");

            if (configFile == null || !configFile.exists()) {
                System.out.println(
                        AnsiOutput.toString(AnsiColor.RED,
                                String.format("AutoCodeConfig.yaml not found in project dir %s", projectDir)
                                , AnsiColor.DEFAULT)
                );
                return null;
            }

            if (configFile.exists()) {
                config = yamlMapper.readValue(configFile, AutoCodeConfig.class);
                System.out.println("AutoCodeConfig file loaded............ ");

//                System.out.println("Generators..........." + config.getGenerators().size());
//                System.out.println(config.);

                List<ResourceSample> resourceSamples = loadResourceSamples(projectDir);
                if (!CollectionUtils.isEmpty(resourceSamples )) {
                    config.setResourceSamples(resourceSamples );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getLocalizedMessage(), e);
            System.out.println(String.format("Failed with error [%s]", e.getLocalizedMessage()));
            CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.ERROR, "", String.format("Failed with error [%s]", e.getLocalizedMessage()));
            throw e;
        }

        return config;
    }


    public static List<ResourceSample> loadResourceSamples(String projectDir) throws Exception {
        List<ResourceSample> sampleRequests = null;

        if (projectDir == null) {
            return null;
        }
        try {

            ObjectMapper yamlMapper = new ObjectMapper();

            System.out.println("loading "+RESOURCE_SAMPLES_FILE+"...");
            CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, RESOURCE_SAMPLES_FILE, "Loading");
            if (!StringUtils.endsWithIgnoreCase(projectDir, "/")) {
                projectDir += "/";
            }
            File sampleRequestsFile = FileUtils.getFile(new File(projectDir), RESOURCE_SAMPLES_FILE);

            if (sampleRequestsFile == null || !sampleRequestsFile.exists()) {
                System.out.println(
                        AnsiOutput.toString(AnsiColor.RED,
                                String.format(RESOURCE_SAMPLES_FILE + " not found in project dir %s", projectDir)
                                , AnsiColor.DEFAULT)
                );
                return null;
            }

            if (sampleRequestsFile.exists()) {
                ResourceSample[] sampleRequestsArr = yamlMapper.readValue(sampleRequestsFile, ResourceSample[].class);

                if (! ArrayUtils.isEmpty(sampleRequestsArr)) {
                    sampleRequests = Arrays.asList(sampleRequestsArr);
                }
            }

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
            System.out.println(String.format("Failed with error [%s]", e.getLocalizedMessage()));
            CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.ERROR, "", String.format("Failed with error [%s]", e.getLocalizedMessage()));
            throw e;
        }

        return sampleRequests;
    }


}
