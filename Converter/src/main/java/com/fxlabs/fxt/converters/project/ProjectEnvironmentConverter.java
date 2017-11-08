package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectEnvironment;
import com.fxlabs.fxt.dao.entity.users.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectEnvironmentConverter extends BaseConverter<ProjectEnvironment, com.fxlabs.fxt.dto.project.ProjectEnvironment> {
}
