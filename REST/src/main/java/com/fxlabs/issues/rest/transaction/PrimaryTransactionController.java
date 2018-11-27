package com.fxlabs.issues.rest.transaction;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.transaction.PrimaryTransaction;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.transaction.PrimaryTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.PRIMARY_TRANSACTION_BASE)
public class PrimaryTransactionController {

    private PrimaryTransactionService primaryTransactionService;


    @Autowired
    public PrimaryTransactionController(
            PrimaryTransactionService primaryTransactionService) {
        this.primaryTransactionService = primaryTransactionService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/primary-transaction/{id}", method = RequestMethod.GET)
    public Response<PrimaryTransaction> findPrimaryTransactionById(@PathVariable("id") String id) {


        return primaryTransactionService.findPrimaryTransactionById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/primary-transaction", method = RequestMethod.GET)
    public Response<List<PrimaryTransaction>> findAllPrimaryTransaction() {


        return primaryTransactionService.findAllPrimaryTransaction(SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/primary-transaction", method = RequestMethod.POST)
    public Response<PrimaryTransaction> add(@RequestBody PrimaryTransaction request) {
        return primaryTransactionService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/primary-transaction", method = RequestMethod.PUT)
    public Response<PrimaryTransaction> update(@RequestBody PrimaryTransaction request) {
        return primaryTransactionService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/primary-transaction/{id}", method = RequestMethod.DELETE)
    public Response<PrimaryTransaction> deleteById(@PathVariable("id") String id) {
        return primaryTransactionService.delete(id, SecurityUtil.getCurrentAuditor());
    }

}
