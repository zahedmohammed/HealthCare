package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.alerts.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Intesar Shannan Mohammed
 */
public interface AlertRepository extends JpaRepository<Alert, String> {
}
