package com.fxlabs.fxt.rest.clusters;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.CloudAccount;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.clusters.CloudAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 * @author Mohammed Shoukath Ali
 * @since 4/28/2018
 */
@RestController
@RequestMapping(CLOUD_ACCOUNT_BASE)
public class AccountController {

    private CloudAccountService cloudAccountService;

    @Autowired
    public AccountController(
            CloudAccountService cloudAccountService) {
        this.cloudAccountService = cloudAccountService;
    }

    @Secured(ROLE_USER)
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<CloudAccount>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return cloudAccountService.findAll(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/accounttype/{accountType}" , method = RequestMethod.GET)
    public Response<List<CloudAccount>> findByAccountType( @PathVariable("accountType") String accountType) {
        return cloudAccountService.findByAccountType(accountType, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<CloudAccount> findById(@PathVariable("id") String id) {
        return cloudAccountService.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<CloudAccount>> create(@Valid @RequestBody List<CloudAccount> dtos) {
        //return service.save(dtos);
        return null;
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<CloudAccount> create(@Valid @RequestBody CloudAccount dto) {
        return cloudAccountService.create(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<CloudAccount> update(@Valid @RequestBody CloudAccount dto) {
        return cloudAccountService.update(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<CloudAccount> delete(@PathVariable("id") String id) {
        return cloudAccountService.delete(id, SecurityUtil.getCurrentAuditor());
    }


}
