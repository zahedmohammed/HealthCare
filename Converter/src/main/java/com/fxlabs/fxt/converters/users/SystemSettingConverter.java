package com.fxlabs.fxt.converters.users;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.users.SystemSetting;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface SystemSettingConverter extends BaseConverter<SystemSetting, com.fxlabs.fxt.dto.users.SystemSetting> {
}
