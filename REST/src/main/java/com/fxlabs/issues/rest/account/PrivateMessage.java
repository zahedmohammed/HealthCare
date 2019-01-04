package com.fxlabs.issues.rest.account;


import com.fxlabs.issues.dto.account.Appointment;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.account.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.APPOINTMENT_BASE)
@Validated
public class PrivateMessage {

    private AppointmentService appointmentService;

    @Autowired
    public PrivateMessage(
            AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Appointment> findAppointmentById(@PathVariable("id") String id) {
        return appointmentService.findAppointmentById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<Appointment>> findAllAppointments(@RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(2000) Integer pageSize) {
        return appointmentService.findAllAppointments(pageSize, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Appointment> add(@RequestBody Appointment request) {
        return appointmentService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Appointment> update(@RequestBody Appointment request) {
        return appointmentService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Appointment> deleteById(@PathVariable("id") String id) {
        return appointmentService.delete(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response<Appointment> findByContactName(@RequestParam(value = "contactName", required = true) String contactName) {
        return appointmentService.findByContactName(contactName, SecurityUtil.getCurrentAuditor());
    }

}
