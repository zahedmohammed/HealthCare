package com.fxlabs.issues.converters.account;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.account.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountConverter extends BaseConverter<BankAccount, com.fxlabs.issues.dto.account.BankAccount> {
}
