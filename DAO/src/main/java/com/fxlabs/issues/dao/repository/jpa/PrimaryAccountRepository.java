package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.PrimaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryAccountRepository extends JpaRepository<PrimaryAccount, String> {
}
