package com.fxlabs.issues.converters.transaction;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrimaryTransactionConverter extends BaseConverter<PrimaryTransaction, com.fxlabs.issues.dto.transaction.PrimaryTransaction> {
}
