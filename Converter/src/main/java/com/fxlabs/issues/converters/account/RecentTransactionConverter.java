package com.fxlabs.issues.converters.account;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.account.RecentTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecentTransactionConverter extends BaseConverter<RecentTransaction, com.fxlabs.issues.dto.account.RecentTransaction> {
}
