package com.fxlabs.issues.rest.account;


import com.fxlabs.issues.dto.account.Transfers;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.account.TransfersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.TRANSSFERS_BASE)
@Validated
public class TransfersController {

    private TransfersService transfersService;

    @Autowired
    public TransfersController(
            TransfersService transfersService) {
        this.transfersService = transfersService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Transfers> findTransfersById(@PathVariable("id") String id) {
        return transfersService.findTransfersById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<Transfers>> findAllTransfers(@RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(2000) Integer pageSize) {
        return transfersService.findAllTransfers(pageSize, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER,  ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Transfers> add(@RequestBody Transfers request) {
        return transfersService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER,  ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Transfers> update(@RequestBody Transfers request) {
        return transfersService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Transfers> deleteById(@PathVariable("id") String id) {
        return transfersService.delete(id, SecurityUtil.getCurrentAuditor());
    }

}
