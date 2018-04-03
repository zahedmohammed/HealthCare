package com.fxlabs.fxt.converters.notify;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.notify.NotificationAccount;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 */
@Mapper(componentModel = "spring")
public interface NotificationAccountConverter extends BaseConverter<NotificationAccount, com.fxlabs.fxt.dto.notify.NotificationAccount> {
}
