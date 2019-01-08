package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.RecentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecentTransactionRepository extends JpaRepository<RecentTransaction, String> {

  //  Optional<RecentTransaction> findByContactName(String contactName);
}
