package com.fxlabs.fxt.converters.project;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public abstract class TestSuiteMinConverter implements BaseConverter<TestSuiteMin, com.fxlabs.fxt.dto.project.TestSuite> {

    public String copyTestSuiteToYaml(TestSuiteMin sourceDest){
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        String yamlFormat = null;
        try {
            yamlFormat = yamlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sourceDest);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
//        Yaml yaml = new Yaml();
//        String yamlFormat = yaml.dump(sourceDest);
        return  yamlFormat;
    }


    public com.fxlabs.fxt.dto.project.TestSuite copyYamlToTestSuite(String yaml) throws IOException {

        if (StringUtils.isEmpty(yaml)) {
            return null;
        }
        ObjectMapper mapperObj = new ObjectMapper(new YAMLFactory());
        return mapperObj.readValue(yaml, com.fxlabs.fxt.dto.project.TestSuite.class);
    }

    public TestSuiteMin copyYamlToTestSuiteMin(String yaml) throws IOException {

        if (StringUtils.isEmpty(yaml)) {
            return null;
        }
        ObjectMapper mapperObj = new ObjectMapper(new YAMLFactory());
        return mapperObj.readValue(yaml, TestSuiteMin.class);
    }

}
