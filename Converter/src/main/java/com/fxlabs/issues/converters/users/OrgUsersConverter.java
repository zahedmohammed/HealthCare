package com.fxlabs.issues.converters.users;


import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.users.OrgUsers;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface OrgUsersConverter extends BaseConverter<OrgUsers, com.fxlabs.issues.dto.users.OrgUsers> {
}
