package com.fxlabs.fxt.rest.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Org;
import com.fxlabs.fxt.dto.users.OrgUsers;
import com.fxlabs.fxt.dto.users.SystemSetting;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.users.OrgService;
import com.fxlabs.fxt.services.users.OrgUsersService;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(ORG_BASE)
public class OrgController {

    private OrgUsersService orgUsersService;
    private OrgService orgService;

    @Autowired
    public OrgController(OrgUsersService orgUsersService, OrgService orgService) {
        this.orgUsersService = orgUsersService;
        this.orgService = orgService;
    }

    @RequestMapping(value = "by-user", method = RequestMethod.GET)
    public Response<List<OrgUsers>> findOrgUsers(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                 @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return orgUsersService.findByAccess(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Org>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                            @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return orgService.findAll(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Org> findById(@PathVariable("id") String id) {
        return orgService.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Org> create(@RequestBody Org request) {
        return orgService.save(request, SecurityUtil.getCurrentAuditor());
    }


    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Org> update(@Valid @RequestBody Org dto) {
        return orgService.update(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Org> delete(@PathVariable("id") String id) {
        return orgService.delete(id, SecurityUtil.getCurrentAuditor());
    }


}
