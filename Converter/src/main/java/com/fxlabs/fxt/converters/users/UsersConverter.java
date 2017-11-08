package com.fxlabs.fxt.converters.users;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.users.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersConverter extends BaseConverter<Users, com.fxlabs.fxt.dto.users.Users> {
}
