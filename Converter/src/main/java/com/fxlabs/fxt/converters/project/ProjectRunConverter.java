package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectRun;
import com.fxlabs.fxt.dao.entity.users.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectRunConverter extends BaseConverter<ProjectRun, com.fxlabs.fxt.dto.project.ProjectRun> {
}
