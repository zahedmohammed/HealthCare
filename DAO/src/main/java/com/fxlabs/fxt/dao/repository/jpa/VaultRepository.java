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

    Page<Vault> findByCreatedBy(String user, org.springframework.data.domain.Pageable pageable);

    Optional<Vault> findByOrgNameAndKey(String org, String key);
}
