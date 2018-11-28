package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.Recepient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecepientRepository extends JpaRepository<Recepient, String> {
}
