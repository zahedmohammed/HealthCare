package com.fxlabs.fxt.converters.users;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface ProjectUsersConverter extends BaseConverter<ProjectUsers, com.fxlabs.fxt.dto.users.ProjectUsers> {
}
