package com.fxlabs.fxt.converters.users;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.users.Org;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrgConverter extends BaseConverter<Org, com.fxlabs.fxt.dto.users.Org> {
}
