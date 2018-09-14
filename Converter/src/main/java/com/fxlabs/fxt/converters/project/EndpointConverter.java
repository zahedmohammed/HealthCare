package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.Endpoint;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Luqman Shareef
 */
@Mapper(componentModel = "spring")
public interface EndpointConverter extends BaseConverter<Endpoint, com.fxlabs.fxt.dto.project.Endpoint> {
}
