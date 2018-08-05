package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
                dest.setAssertionsText(joiner.toString());
            });
        }

        if (!CollectionUtils.isEmpty(dest.getTags())) {
            StringJoiner joiner = new StringJoiner("\n");
            dest.getAssertions().forEach(a -> {
                joiner.add(a);
                dest.setTagsText(joiner.toString());
            });
        }

        if (!CollectionUtils.isEmpty(dest.getAuthors())) {
            StringJoiner joiner = new StringJoiner("\n");
            dest.getAssertions().forEach(a -> {
                joiner.add(a);
                dest.setAuthorsText(joiner.toString());
            });
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


    }
}
