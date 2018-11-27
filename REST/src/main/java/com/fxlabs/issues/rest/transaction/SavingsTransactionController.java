package com.fxlabs.issues.rest.transaction;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.transaction.SavingsTransaction;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.transaction.SavingsTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.SAVINGS_TRANSACTION_BASE)
public class SavingsTransactionController {

    private SavingsTransactionService savingsTransactionService;


    @Autowired
    public SavingsTransactionController(
            SavingsTransactionService savingsTransactionService) {
        this.savingsTransactionService = savingsTransactionService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/savings-transaction/{id}", method = RequestMethod.GET)
    public Response<SavingsTransaction> findSavingsTransactionById(@PathVariable("id") String id) {


        return savingsTransactionService.findSavingsTransactionById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/savings-transaction", method = RequestMethod.GET)
    public Response<List<SavingsTransaction>> findAllSavingsTransaction() {


        return savingsTransactionService.findAllSavingsTransaction(SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/savings-transaction", method = RequestMethod.POST)
    public Response<SavingsTransaction> add(@RequestBody SavingsTransaction request) {
        return savingsTransactionService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/savings-transaction", method = RequestMethod.PUT)
    public Response<SavingsTransaction> update(@RequestBody SavingsTransaction request) {
        return savingsTransactionService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/savings-transaction/{id}", method = RequestMethod.DELETE)
    public Response<SavingsTransaction> deleteById(@PathVariable("id") String id) {
        return savingsTransactionService.delete(id, SecurityUtil.getCurrentAuditor());
    }

}
