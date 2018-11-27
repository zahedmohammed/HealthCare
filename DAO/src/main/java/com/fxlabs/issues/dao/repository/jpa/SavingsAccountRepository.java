package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, String> {
}
