package com.fxlabs.fxt.converters.vault;


import com.fxlabs.fxt.converters.base.BaseConverter;
import com.fxlabs.fxt.dao.entity.vault.Vault;
import org.mapstruct.Mapper;

/**
 * @author Intesar Shannan Mohammed
 */
@Mapper(componentModel = "spring")
public interface VaultConverter extends BaseConverter<Vault, com.fxlabs.fxt.dto.vault.Vault> {
}
