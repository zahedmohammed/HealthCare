package com.fxlabs.issues.converters.account;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.account.Transfers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransfersConverter extends BaseConverter<Transfers, com.fxlabs.issues.dto.account.Transfers> {
}
