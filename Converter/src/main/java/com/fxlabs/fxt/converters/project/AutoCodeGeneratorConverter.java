package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeGenerator;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public interface AutoCodeGeneratorConverter extends BaseConverter<AutoCodeGenerator, com.fxlabs.fxt.dto.project.AutoCodeGenerator> {
}
