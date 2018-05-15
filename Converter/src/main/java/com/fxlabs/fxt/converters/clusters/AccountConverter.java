package com.fxlabs.fxt.converters.clusters;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
@Mapper(componentModel = "spring")
public interface AccountConverter extends BaseConverter<Account, com.fxlabs.fxt.dto.clusters.Account> {

    @Mapping(target = "secretKey", ignore = true)
    com.fxlabs.fxt.dto.clusters.Account convertToDto(Account entity);
}
