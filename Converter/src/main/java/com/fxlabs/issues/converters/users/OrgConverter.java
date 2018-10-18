package com.fxlabs.issues.converters.users;


import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.users.Org;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface OrgConverter extends BaseConverter<Org, com.fxlabs.issues.dto.users.Org> {
}
