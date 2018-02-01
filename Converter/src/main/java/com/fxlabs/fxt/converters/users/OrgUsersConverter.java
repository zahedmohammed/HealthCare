package com.fxlabs.fxt.converters.users;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrgUsersConverter extends BaseConverter<OrgUsers, com.fxlabs.fxt.dto.users.OrgUsers> {
}
