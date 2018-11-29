package com.fxlabs.issues.services.account;

import com.fxlabs.issues.converters.account.AppointmentConverter;
import com.fxlabs.issues.dao.entity.users.Users;
import com.fxlabs.issues.dto.account.Appointment;
import com.fxlabs.issues.dao.repository.jpa.AppointmentRepository;
import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericServiceImpl;
import com.github.javafaker.Faker;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
        List<com.fxlabs.issues.dao.entity.account.Appointment> appointmentList = new ArrayList<>();
        Faker faker = new Faker();
        String size = String.valueOf(faker.random().nextInt(1, 20));
        for (int i = 0; i < 10; i++){
            appointmentList.add(i, data());
            System.out.println("Check Data ----" + appointmentList.toString());
        }

        return new Response<List<Appointment>>(appointmentConverter.convertToDtos(appointmentList));
    }

    private com.fxlabs.issues.dao.entity.account.Appointment data() {

        com.fxlabs.issues.dao.entity.account.Appointment appointmentData = new com.fxlabs.issues.dao.entity.account.Appointment();
        Faker faker = new Faker();
        Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String location = String.valueOf(faker.random());
        String description = String.valueOf(faker.random());
        boolean confirmed = Boolean.valueOf(faker.bool().bool()) ;

        appointmentData.setDate(date);
        appointmentData.setLocation(location);
        appointmentData.setDescription(description);
        appointmentData.setConfirmed(confirmed);
        appointmentData.setCreatedDate(faker.date().birthday());
        appointmentData.setModifiedDate(faker.date().birthday());
        appointmentData.setId(faker.idNumber().valid());
        appointmentData.setCreatedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        appointmentData.setModifiedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));

        Users user = new Users();
        user.setAccountNonExpired(faker.bool().bool());
        user.setAccountNonLocked(faker.bool().bool());
        user.setAccountNumber(faker.number().numberBetween(1,10));
        user.setCompany(faker.company().industry());
        user.setEmail(faker.internet().emailAddress());
        user.setLocation(faker.address().fullAddress());
        user.setEnabled(faker.bool().bool());
        user.setJobTitle(faker.job().title());
        user.setName(faker.name().name());
        user.setUsername(faker.name().username());
        user.setCreatedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        user.setId(faker.idNumber().valid());
        user.setCreatedDate(faker.date().birthday());
        user.setModifiedBy(faker.numerify("abcdefghijklmnopqrstuvwxyz123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        user.setModifiedDate(faker.date().birthday());
        appointmentData.setUser(user);

        return appointmentData;
    }

    @Override
    public void isUserEntitled(String s, String user) {

    }
}
