package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.PrimaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrimaryAccountRepository extends JpaRepository<PrimaryAccount, String> {
    Optional<PrimaryAccount> findByIdAndCreatedBy(String id, String currentAuditor);

    Optional<PrimaryAccount> deleteByIdAndCreatedBy(String id, String currentAuditor);
}
