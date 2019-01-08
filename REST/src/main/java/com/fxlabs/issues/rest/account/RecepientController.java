package com.fxlabs.issues.rest.account;

import com.fxlabs.issues.dto.account.Recepient;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.account.RecepientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.RECEPIENT_BASE)
@Validated
public class RecepientController {

    private RecepientService recepientService;

    @Autowired
    public RecepientController(
            RecepientService recepientService) {
        this.recepientService = recepientService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Recepient> findRecepientById(@PathVariable("id") String id) {
        return recepientService.findRecepientById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER,  ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<Recepient>> findAllRecepients(@RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(2000) Integer pageSize) {
        return recepientService.findAllRecepients(pageSize, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Recepient> addRecepient(@RequestBody Recepient request) {
        return recepientService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Recepient> update(@RequestBody Recepient request) {
        return recepientService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Recepient> deleteById(@PathVariable("id") String id) {
        return recepientService.delete(id, SecurityUtil.getCurrentAuditor());
    }

}
