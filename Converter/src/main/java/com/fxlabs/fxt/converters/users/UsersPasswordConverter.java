package com.fxlabs.fxt.converters.users;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.users.UsersPassword;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersPasswordConverter extends BaseConverter<UsersPassword, com.fxlabs.fxt.dto.users.UsersPassword> {
}
