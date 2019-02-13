package com.fxlabs.issues.rest.account;

import com.fxlabs.issues.dto.account.BankAccount;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.account.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.BANK_ACCOUNT_BASE)
public class BankAccountController {

    private BankAccountService bankAccountService;


    @Autowired
    public BankAccountController(
            BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<BankAccount> findBankAccountById(@PathVariable("id") String id) {

        return bankAccountService.findBankAccountById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<BankAccount>> findAllBankAccount(@RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(2000) Integer pageSize) {


        return bankAccountService.findAllBankAccount(pageSize,SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<BankAccount> add(@RequestBody BankAccount request) {
        return bankAccountService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER,  BaseController.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<BankAccount> update(@RequestBody BankAccount request) {
        return bankAccountService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER,  BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Boolean> deleteById(@PathVariable("id") String id) {
        return bankAccountService.deleteBankAccount(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/deposit-amount", method = RequestMethod.PUT)
    public Response<Boolean> deposit(@RequestBody BankAccount request) {
        return bankAccountService.depositAmount(request, SecurityUtil.getCurrentAuditor());
    }
    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/withdraw-amount", method = RequestMethod.PUT)
    public Response<Boolean> withdraw(@RequestBody BankAccount request) {
        return bankAccountService.withdrawAmount(request, SecurityUtil.getCurrentAuditor());
    }
}
