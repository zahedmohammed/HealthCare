package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectDataSet;
import com.fxlabs.fxt.dao.entity.users.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectDataSetConverter extends BaseConverter<ProjectDataSet, com.fxlabs.fxt.dto.project.ProjectDataSet> {
}
