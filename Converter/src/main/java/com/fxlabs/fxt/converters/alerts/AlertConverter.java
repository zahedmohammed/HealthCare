package com.fxlabs.fxt.converters.alerts;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.alerts.Alert;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface AlertConverter extends BaseConverter<Alert, com.fxlabs.fxt.dto.alerts.Alert> {
}
