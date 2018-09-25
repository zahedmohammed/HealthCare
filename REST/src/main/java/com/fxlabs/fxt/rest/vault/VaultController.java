package com.fxlabs.fxt.rest.vault;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.vault.Vault;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.vault.VaultService;
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
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(VAULT_BASE)
@Validated
public class VaultController {

    private VaultService service;

    @Autowired
    public VaultController(VaultService service) {
        this.service = service;
    }


    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Vault>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                         @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(20) Integer pageSize) {

        return service.findAll(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Vault> findById(@PathVariable("id") String id) {

        return service.findById(id, SecurityUtil.getOrgId());
    }


    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Vault> create(@RequestBody Vault request) {

        return service.create(request, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Vault> update(@Valid @RequestBody Vault dto) {

        return service.update(dto, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Vault> delete(@PathVariable("id") String id) {

        return service.delete(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/Search", method = RequestMethod.GET)
    public Response<List<Vault>> search(@RequestParam(value = "keyword", required = false) String keyword,
                                        @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                        @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(20) Integer pageSize) {

        return service.searchVault(keyword, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

}
