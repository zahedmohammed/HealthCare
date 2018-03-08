package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.vault.Vault;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface VaultRepository extends JpaRepository<Vault, String> {
    List<Vault> findByCreatedBy(String user, org.springframework.data.domain.Pageable pageable);
}
