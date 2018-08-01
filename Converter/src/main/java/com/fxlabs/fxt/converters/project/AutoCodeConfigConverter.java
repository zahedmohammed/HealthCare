package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Mohammed Shoukath Ali
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public abstract class AutoCodeConfigConverter implements BaseConverter<AutoCodeConfig, com.fxlabs.fxt.dto.project.AutoCodeConfig> {

    public void copyAssertionsToText(com.fxlabs.fxt.dto.project.AutoCodeConfig dest) {
        dest.getGenerators().forEach(g -> {
            if (!CollectionUtils.isEmpty(g.getAssertions())) {
                StringJoiner joiner = new StringJoiner("\n");
                g.getAssertions().forEach(a -> {
                    joiner.add(a);
                });
                g.setAssertionsText(joiner.toString());
            }
        });

    }

    public void copyAssertionsTextToArray(com.fxlabs.fxt.dto.project.AutoCodeConfig dest) {
        dest.getGenerators().forEach(g -> {
            if (!StringUtils.isEmpty(g.getAssertionsText())) {
                try {
                    List<String> list = Arrays.asList(g.getAssertionsText().split("\n"));
                    g.setAssertions(list);
                } catch (NullPointerException npe) {
                }
            } else {
                g.setAssertions(new ArrayList<>());
            }
        });
    }
}
