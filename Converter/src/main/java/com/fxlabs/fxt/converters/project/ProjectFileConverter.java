package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.ProjectFile;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface ProjectFileConverter extends BaseConverter<ProjectFile, com.fxlabs.fxt.dto.project.ProjectFile> {
}
