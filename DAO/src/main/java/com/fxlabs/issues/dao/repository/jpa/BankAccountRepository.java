package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findByIdAndCreatedBy(String id, String currentAuditor);

    Optional<BankAccount> deleteByIdAndCreatedBy(String id, String currentAuditor);
}
