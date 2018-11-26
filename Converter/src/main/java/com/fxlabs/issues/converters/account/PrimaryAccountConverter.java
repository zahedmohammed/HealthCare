package com.fxlabs.issues.converters.account;

import com.fxlabs.issues.converters.base.BaseConverter;
import com.fxlabs.issues.dao.entity.account.PrimaryAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrimaryAccountConverter extends BaseConverter<PrimaryAccount, com.fxlabs.issues.dto.account.PrimaryAccount> {
}
