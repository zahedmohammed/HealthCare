package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectDataSetConverter extends BaseConverter<TestSuite, com.fxlabs.fxt.dto.project.ProjectDataSet> {
}
