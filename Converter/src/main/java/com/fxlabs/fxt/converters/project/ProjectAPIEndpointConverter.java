package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectApiEndpoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectAPIEndpointConverter extends BaseConverter<ProjectApiEndpoint, com.fxlabs.fxt.dto.project.ProjectAPIEndpoint> {
}
