package com.fxlabs.fxt.converters;


import com.fxlabs.fxt.dao.entity.Users;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UsersConverter extends BaseConverter<Users, com.fxlabs.fxt.dto.Users> {
}
