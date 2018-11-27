package com.fxlabs.issues.converters.account;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.account.PrimaryAccount;
import com.fxlabs.issues.dao.entity.account.SavingsAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SavingsAccountConverter extends BaseConverter<SavingsAccount, com.fxlabs.issues.dto.account.SavingsAccount> {
}
