package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.Transfers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransfersRepository extends JpaRepository<Transfers, String> {

  //  Optional<Transfers> findByContactName(String contactName);
}
