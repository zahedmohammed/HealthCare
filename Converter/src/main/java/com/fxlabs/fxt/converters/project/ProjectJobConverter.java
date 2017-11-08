package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectJob;
import com.fxlabs.fxt.dao.entity.users.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectJobConverter extends BaseConverter<ProjectJob, com.fxlabs.fxt.dto.project.ProjectJob> {
}
