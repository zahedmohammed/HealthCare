package com.fxlabs.fxt.rest.accounts;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.clusters.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Mohammed Luqman Shareef
 * @author Mohammed Shoukath Ali
 * @since 3/20/2018
 * @since 4/28/2018
 */
@RestController
@RequestMapping(ACCOUNT_BASE)
@Validated
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(
            AccountService accountService) {
        this.accountService = accountService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Account>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Max(20) Integer pageSize) {

        return accountService.findAll(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/account-type/{accountType}", method = RequestMethod.GET)
    public Response<List<Account>> findByAccountType(@PathVariable("accountType") String accountType) {
        return accountService.findByAccountType(accountType, SecurityUtil.getOrgId());
    }

    @Secured(ROLE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Account> findById(@PathVariable("id") String id) {
        return accountService.findById(id, SecurityUtil.getOrgId());
    }

    @Secured(ROLE_ADMIN)
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<Account>> create(@Valid @RequestBody List<Account> dtos) {
        //return service.save(dtos);
        return null;
    }

    @Secured(ROLE_ADMIN)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Account> create(@Valid @RequestBody Account dto) {
        return accountService.create(dto, SecurityUtil.getOrgId());
    }

    @Secured(ROLE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Account> update(@Valid @RequestBody Account dto) {
        return accountService.update(dto, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Account> delete(@PathVariable("id") String id) {
        return accountService.delete(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }


}
