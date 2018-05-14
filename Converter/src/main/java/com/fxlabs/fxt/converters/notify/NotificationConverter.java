package com.fxlabs.fxt.converters.notify;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.notify.Notification;
import org.mapstruct.Mapper;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 */
@Mapper(componentModel = "spring")
public interface NotificationConverter extends BaseConverter<Notification, com.fxlabs.fxt.dto.notify.Notification> {
}
