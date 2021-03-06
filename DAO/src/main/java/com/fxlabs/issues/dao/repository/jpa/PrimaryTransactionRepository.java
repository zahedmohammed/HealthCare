package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.transaction.PrimaryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryTransactionRepository extends JpaRepository<PrimaryTransaction, String> {
}
