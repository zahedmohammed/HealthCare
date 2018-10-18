package com.fxlabs.issues.converters.users;


import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.users.SystemSetting;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface SystemSettingConverter extends BaseConverter<SystemSetting, com.fxlabs.issues.dto.users.SystemSetting> {
}
