package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.account.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

}
