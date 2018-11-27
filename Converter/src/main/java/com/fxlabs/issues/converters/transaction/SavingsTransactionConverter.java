package com.fxlabs.issues.converters.transaction;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction;
import com.fxlabs.issues.dao.entity.transaction.SavingsTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SavingsTransactionConverter extends BaseConverter<SavingsTransaction, com.fxlabs.issues.dto.transaction.SavingsTransaction> {
}
