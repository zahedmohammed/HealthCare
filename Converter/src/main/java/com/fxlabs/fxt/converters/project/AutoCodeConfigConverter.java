package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public interface AutoCodeConfigConverter extends BaseConverter<AutoCodeConfig, com.fxlabs.fxt.dto.project.AutoCodeConfig> {
}
