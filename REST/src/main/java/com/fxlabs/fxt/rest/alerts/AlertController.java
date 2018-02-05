package com.fxlabs.fxt.rest.alerts;

import com.fxlabs.fxt.dto.alerts.Alert;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.alerts.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.ALERT_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(ALERT_BASE)
public class AlertController extends BaseController<Alert, String> {

    private AlertService alertService;

    @Autowired
    public AlertController(
            AlertService service) {
        super(service);
        this.alertService = alertService;
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/ref/{id}", method = RequestMethod.GET)
    public Response<List<Alert>> findByProjectId(@PathVariable("id") String refId) {
        return alertService.findRefId(refId, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

}
