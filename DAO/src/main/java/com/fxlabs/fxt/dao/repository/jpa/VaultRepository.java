package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.vault.Vault;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface VaultRepository extends JpaRepository<Vault, String> {

    Page<Vault> findByOrgId(String org, org.springframework.data.domain.Pageable pageable);

    Optional<Vault> findByOrgNameAndKey(String org, String key);

    Optional<Vault> findByKeyAndOrgId(String key, String org);
}
