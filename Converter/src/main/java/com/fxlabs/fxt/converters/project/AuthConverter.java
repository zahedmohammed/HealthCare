package com.fxlabs.fxt.converters.project;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.project.Auth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthConverter extends BaseConverter<Auth, com.fxlabs.fxt.dto.project.Auth> {
}
