package com.fxlabs.fxt.converters;


import com.fxlabs.fxt.dao.entity.Org;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrgConverter extends BaseConverter<Org, com.fxlabs.fxt.dto.Org> {
}
