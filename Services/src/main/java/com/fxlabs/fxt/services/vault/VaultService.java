package com.fxlabs.fxt.services.vault;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.vault.Vault;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;


import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface VaultService extends GenericService<Vault, String> {

    public Response<String> findByName(String name, String organisation);

    Response<com.fxlabs.fxt.dto.vault.Vault> create(com.fxlabs.fxt.dto.vault.Vault dto, String org, String user);

    Response<com.fxlabs.fxt.dto.vault.Vault> update(com.fxlabs.fxt.dto.vault.Vault dto, String org, String user);

    Response<com.fxlabs.fxt.dto.vault.Vault> delete(String id, String org, String user);

    Response<List<com.fxlabs.fxt.dto.vault.Vault>> searchVault(String keyword, String org, Pageable pageable);

    }
