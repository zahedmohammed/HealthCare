package com.fxlabs.issues.converters.users;


import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.users.Users;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface UsersConverter extends BaseConverter<Users, com.fxlabs.issues.dto.users.Users> {
}
