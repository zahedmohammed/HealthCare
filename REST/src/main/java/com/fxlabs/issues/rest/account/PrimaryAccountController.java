package com.fxlabs.issues.rest.account;

import com.fxlabs.issues.dto.account.PrimaryAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.account.PrimaryAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.PRIMARY_ACCOUNT_BASE)
public class PrimaryAccountController {

    private PrimaryAccountService primaryAccountService;


    @Autowired
    public PrimaryAccountController(
            PrimaryAccountService primaryAccountService) {
        this.primaryAccountService = primaryAccountService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/primary-account/{id}", method = RequestMethod.GET)
    public Response<PrimaryAccount> findPrimaryAccountById(@PathVariable("id") String id) {


        return primaryAccountService.findPrimaryAccountById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/primary-account", method = RequestMethod.GET)
    public Response<List<PrimaryAccount>> findAllPrimaryAccount(@RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(2000) Integer pageSize) {


        return primaryAccountService.findAllPrimaryAccount(pageSize,SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/primary-account", method = RequestMethod.POST)
    public Response<PrimaryAccount> add(@RequestBody PrimaryAccount request) {
        return primaryAccountService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/primary-account", method = RequestMethod.PUT)
    public Response<PrimaryAccount> update(@RequestBody PrimaryAccount request) {
        return primaryAccountService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/primary-account/{id}", method = RequestMethod.DELETE)
    public Response<PrimaryAccount> deleteById(@PathVariable("id") String id) {
        return primaryAccountService.delete(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/deposit-amount", method = RequestMethod.PUT)
    public Response<Boolean> deposit(@RequestBody PrimaryAccount request) {
        return primaryAccountService.depositAmount(request, SecurityUtil.getCurrentAuditor());
    }
    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/withdraw-amount", method = RequestMethod.PUT)
    public Response<Boolean> withdraw(@RequestBody PrimaryAccount request) {
        return primaryAccountService.withdrawAmount(request, SecurityUtil.getCurrentAuditor());
    }
}
