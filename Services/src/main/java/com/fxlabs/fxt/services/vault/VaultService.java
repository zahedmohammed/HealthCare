package com.fxlabs.fxt.services.vault;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.vault.Vault;
import com.fxlabs.fxt.services.base.GenericService;

/**
 * @author Intesar Shannan Mohammed
 */
public interface VaultService extends GenericService<Vault, String> {

    public Response<String> findByName(String name);
}
