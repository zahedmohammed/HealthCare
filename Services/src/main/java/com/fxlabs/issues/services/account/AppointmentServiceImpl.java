package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.AppointmentConverter;
import com.fxlabs.issues.dto.account.Appointment;
import com.fxlabs.issues.dao.repository.jpa.AppointmentRepository;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class AppointmentServiceImpl extends GenericServiceImpl<com.fxlabs.issues.dao.entity.account.Appointment, Appointment, String> implements AppointmentService {

    private AppointmentConverter appointmentConverter;
    private AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentConverter appointmentConverter) {
        super(appointmentRepository, appointmentConverter);
        this.appointmentConverter = appointmentConverter;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Response<Appointment> findAppointmentById(String id, String currentAuditor) {

        //Todo - isuserentitled
        Optional<com.fxlabs.issues.dao.entity.account.Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (!optionalAppointment.isPresent())
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for appointment"));
        return new Response<Appointment>(appointmentConverter.convertToDto(optionalAppointment.get()));

    }

    @Override
    public Response<List<Appointment>> findAllAppointments(String currentAuditor) {
        List<com.fxlabs.issues.dao.entity.account.Appointment> appointmentList = appointmentRepository.findAll();
        return new Response<List<Appointment>>(appointmentConverter.convertToDtos(appointmentList));
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
