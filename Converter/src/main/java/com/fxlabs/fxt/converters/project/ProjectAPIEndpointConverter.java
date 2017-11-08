package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectAPIEndpoint;
import com.fxlabs.fxt.dao.entity.users.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectAPIEndpointConverter extends BaseConverter<ProjectAPIEndpoint, com.fxlabs.fxt.dto.project.ProjectAPIEndpoint> {
}
