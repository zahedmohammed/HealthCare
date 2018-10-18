package com.fxlabs.issues.converters.project;


import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.project.Project;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface ProjectConverter extends BaseConverter<Project, com.fxlabs.issues.dto.project.Project> {
}
