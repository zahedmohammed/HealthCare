package com.fxlabs.fxt.converters.alerts;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.event.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author  Mohammed Shoukath Ali
 */
@Mapper(componentModel = "spring")
public interface EventConverter extends BaseConverter<Event, com.fxlabs.fxt.dto.events.Event> {
}
