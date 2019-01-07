package com.fxlabs.issues.rest.account;


import com.fxlabs.issues.dto.account.RecentTransaction;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.account.RecentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.RECENTTRANSACTION_BASE)
@Validated
public class RecentTransactionController {

    private RecentTransactionService recentTransactionService;

    @Autowired
    public RecentTransactionController(
            RecentTransactionService recentTransactionService) {
        this.recentTransactionService = recentTransactionService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<RecentTransaction> findRecentTransactionById(@PathVariable("id") String id) {
        return recentTransactionService.findRecentTransactionById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<RecentTransaction>> findAllRecentTransactions(@RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(2000) Integer pageSize) {
        return recentTransactionService.findAllRecentTransactions(pageSize, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<RecentTransaction> add(@RequestBody RecentTransaction request) {
        return recentTransactionService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<RecentTransaction> update(@RequestBody RecentTransaction request) {
        return recentTransactionService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<RecentTransaction> deleteById(@PathVariable("id") String id) {
        return recentTransactionService.delete(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Response<RecentTransaction> findByContactName(@RequestParam(value = "contactName", required = true) String contactName) {
        return recentTransactionService.findByContactName(contactName, SecurityUtil.getCurrentAuditor());
    }

}
