package com.fxlabs.fxt.converters.project;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public abstract class TestSuiteConverter implements BaseConverter<TestSuite, com.fxlabs.fxt.dto.project.TestSuite> {


    public void copyArraysToText(com.fxlabs.fxt.dto.project.TestSuite dest) {


        if (!CollectionUtils.isEmpty(dest.getAssertions())) {
            StringJoiner joiner = new StringJoiner("\n");
            dest.getAssertions().forEach(a -> {
                joiner.add(a);
            });
            dest.setAssertionsText(joiner.toString());
        }

        if (!CollectionUtils.isEmpty(dest.getTags())) {
            StringJoiner joiner = new StringJoiner("\n");
            dest.getTags().forEach(a -> {
                joiner.add(a);
            });
            dest.setTagsText(joiner.toString());
        }

        if (!CollectionUtils.isEmpty(dest.getAuthors())) {
            StringJoiner joiner = new StringJoiner("\n");
            dest.getAuthors().forEach(a -> {
                joiner.add(a);
            });
            dest.setAuthorsText(joiner.toString());
        }

        if (!CollectionUtils.isEmpty(dest.getInit())) {
            StringJoiner joiner = new StringJoiner("\n");
            dest.getInit().forEach(a -> {
                joiner.add(a);
            });
            dest.setInitText(joiner.toString());
        }

        if (!CollectionUtils.isEmpty(dest.getCleanup())) {
            StringJoiner joiner = new StringJoiner("\n");
            dest.getCleanup().forEach(a -> {
                joiner.add(a);
            });
            dest.setCleanupText(joiner.toString());
        }

        if (!CollectionUtils.isEmpty(dest.getHeaders())) {
            StringJoiner joiner = new StringJoiner("\n");
            dest.getHeaders().forEach(a -> {
                joiner.add(a);
            });
            dest.setHeadersText(joiner.toString());
        }
    }

    public void copyTextToArray(com.fxlabs.fxt.dto.project.TestSuite dest) {

        //Assertions
        if (!StringUtils.isEmpty(dest.getAssertionsText())) {
            try {
                List<String> list = Arrays.asList(dest.getAssertionsText().split("\n"));
                dest.setAssertions(list);
            } catch (NullPointerException npe) {
            }
        } else {
            dest.setAssertions(new ArrayList<>());
        }


        //Tags
        if (!StringUtils.isEmpty(dest.getTagsText())) {
            try {
                List<String> list = Arrays.asList(dest.getTagsText().split("\n"));
                dest.setTags(list);
            } catch (NullPointerException npe) {
            }
        } else {
            dest.setTags(new ArrayList<>());
        }

        if (!StringUtils.isEmpty(dest.getAuthorsText())) {
            try {
                List<String> list = Arrays.asList(dest.getAuthorsText().split("\n"));
                dest.setAuthors(list);
            } catch (NullPointerException npe) {
            }
        } else {
            dest.setAuthors(new ArrayList<>());
        }

        if (!StringUtils.isEmpty(dest.getInitText())) {
            try {
                List<String> list = Arrays.asList(dest.getInitText().split("\n"));
                dest.setInit(list);
            } catch (NullPointerException npe) {
            }
        } else {
            dest.setInit(new ArrayList<>());
        }

        if (!StringUtils.isEmpty(dest.getHeadersText())) {
            try {
                List<String> list = Arrays.asList(dest.getHeadersText().split("\n"));
                dest.setHeaders(list);
            } catch (NullPointerException npe) {
            }
        } else {
            dest.setHeaders(new ArrayList<>());
        }

        if (!StringUtils.isEmpty(dest.getCleanupText())) {
            try {
                List<String> list = Arrays.asList(dest.getCleanupText().split("\n"));
                dest.setCleanup(list);
            } catch (NullPointerException npe) {
            }
        } else {
            dest.setCleanup(new ArrayList<>());
        }
    }



    public com.fxlabs.fxt.dto.project.TestSuite copyYamlToTestSuite(String yaml) throws IOException {

        if (StringUtils.isEmpty(yaml)) {
            return null;
        }
        ObjectMapper mapperObj = new ObjectMapper(new YAMLFactory());
        return mapperObj.readValue(yaml, com.fxlabs.fxt.dto.project.TestSuite.class);
    }

    public void copyTestSuiteToYaml(com.fxlabs.fxt.dto.project.TestSuite sourceDest){
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        String yamlFormat = null;
        try {
            yamlFormat = yamlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sourceDest);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
//        Yaml yaml = new Yaml();
//        String yamlFormat = yaml.dump(sourceDest);
        sourceDest.setYaml(yamlFormat);
    }
}
