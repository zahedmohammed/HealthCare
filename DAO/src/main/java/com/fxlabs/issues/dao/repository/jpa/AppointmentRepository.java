package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    Optional<Appointment> findByContactName(String contactName);
}
