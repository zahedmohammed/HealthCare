package com.fxlabs.issues.converters.users;


import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.users.UsersPassword;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface UsersPasswordConverter extends BaseConverter<UsersPassword, com.fxlabs.issues.dto.users.UsersPassword> {
}
