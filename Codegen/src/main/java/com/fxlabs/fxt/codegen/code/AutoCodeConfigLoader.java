package com.fxlabs.fxt.codegen.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.dto.project.RequestMapping;
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
                List<RequestMapping> requestMappings = loadRequestMappings(projectDir);
                if (!CollectionUtils.isEmpty(requestMappings)) {
                    config.setRequestMappings(requestMappings);
                }
            }

        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage(), e);
            System.out.println(String.format("Failed with error [%s]", e.getLocalizedMessage()));
            CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.ERROR, "", String.format("Failed with error [%s]", e.getLocalizedMessage()));
            throw e;
        }

        return config;
    }


    public static List<RequestMapping> loadRequestMappings(String projectDir) throws Exception {
        List<RequestMapping> sampleRequests = null;

        if (projectDir == null) {
            return null;
        }
        try {

            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

            System.out.println("loading RequestMappings.yaml...");
            CodegenThreadUtils.taskLogger.get().append(BotLogger.LogType.INFO, "RequestMappings.yaml", "Loading");
            if (!StringUtils.endsWithIgnoreCase(projectDir, "/")) {
                projectDir += "/";
            }
            File sampleRequestsFile = FileUtils.getFile(new File(projectDir), "RequestMappings.yaml");

            if (sampleRequestsFile == null || !sampleRequestsFile.exists()) {
                System.out.println(
                        AnsiOutput.toString(AnsiColor.RED,
                                String.format("RequestMappings.yaml not found in project dir %s", projectDir)
                                , AnsiColor.DEFAULT)
                );
                return null;
            }

            if (sampleRequestsFile.exists()) {
                RequestMapping[] sampleRequestsArr = yamlMapper.readValue(sampleRequestsFile, RequestMapping[].class);

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
