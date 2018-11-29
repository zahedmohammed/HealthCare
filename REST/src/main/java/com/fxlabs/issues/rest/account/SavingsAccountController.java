package com.fxlabs.issues.rest.account;

import com.fxlabs.issues.dto.account.SavingsAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.account.SavingsAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@Validated
@RequestMapping(BaseController.SAVINGS_ACCOUNT_BASE)
public class SavingsAccountController {

    private SavingsAccountService savingsAccountService;


    @Autowired
    public SavingsAccountController(
            SavingsAccountService savingsAccountService) {
        this.savingsAccountService = savingsAccountService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/savings-account/{id}", method = RequestMethod.GET)
    public Response<SavingsAccount> findSavingsAccountById(@PathVariable("id") String id) {


        return savingsAccountService.findSavingsAccountById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/savings-account", method = RequestMethod.GET)
    public Response<List<SavingsAccount>> findAllSavingsAccount(@RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(2000) Integer pageSize) {


        return savingsAccountService.findAllSavingsAccount(pageSize,SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/savings-account", method = RequestMethod.POST)
    public Response<SavingsAccount> add(@RequestBody SavingsAccount request) {
        return savingsAccountService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/savings-account", method = RequestMethod.PUT)
    public Response<SavingsAccount> update(@RequestBody SavingsAccount request) {
        return savingsAccountService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/savings-account/{id}", method = RequestMethod.DELETE)
    public Response<SavingsAccount> deleteById(@PathVariable("id") String id) {
        return savingsAccountService.delete(id, SecurityUtil.getCurrentAuditor());
    }
}
