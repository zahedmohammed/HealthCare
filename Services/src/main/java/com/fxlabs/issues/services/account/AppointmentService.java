package com.fxlabs.issues.services.account;

import com.fxlabs.issues.dto.account.Appointment;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

public interface AppointmentService extends GenericService<Appointment, String> {

    Response<Appointment> findAppointmentById(String id, String currentAuditor);

    Response<List<Appointment>> findAllAppointments(Integer pageSize,String currentAuditor);

   }
