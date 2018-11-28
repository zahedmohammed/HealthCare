package com.fxlabs.issues.converters.account;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.account.Recepient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecepientConverter extends BaseConverter<Recepient, com.fxlabs.issues.dto.account.Recepient> {

}
