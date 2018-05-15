package com.fxlabs.fxt.services.vault;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.vault.Vault;
import com.fxlabs.fxt.services.base.GenericService;

/**
 * @author Intesar Shannan Mohammed
 */
public interface VaultService extends GenericService<Vault, String> {

    public Response<String> findByName(String name);

    Response<com.fxlabs.fxt.dto.vault.Vault> create(com.fxlabs.fxt.dto.vault.Vault dto, String org, String user);

    Response<com.fxlabs.fxt.dto.vault.Vault> update(com.fxlabs.fxt.dto.vault.Vault dto, String org, String user);

    Response<com.fxlabs.fxt.dto.vault.Vault> delete(String id, String org, String user);
}
