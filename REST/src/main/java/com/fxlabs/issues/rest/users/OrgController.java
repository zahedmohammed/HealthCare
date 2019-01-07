package com.fxlabs.issues.rest.users;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.users.Member;
import com.fxlabs.issues.dto.users.Org;
import com.fxlabs.issues.dto.users.OrgUsers;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.users.OrgService;
import com.fxlabs.issues.services.users.OrgUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

/**
 * Mohammed Shoukath Ali
 */
@RestController
@RequestMapping(BRANCH_BASE)
public class OrgController {

    private OrgUsersService orgUsersService;
    private OrgService orgService;

    @Autowired
    public OrgController(OrgUsersService orgUsersService, OrgService orgService) {
        this.orgUsersService = orgUsersService;
        this.orgService = orgService;
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "by-user", method = RequestMethod.GET)
    public Response<List<OrgUsers>> findOrgUsers(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                 @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(100) Integer pageSize) {

        if (pageSize > 10000){
            DDosUtil.hang = true;
            try {
                Thread.sleep(10 * 1000);
            }catch(Exception ex){}
            finally {
                DDosUtil.hang = false;
            }
        }
        return orgUsersService.findByAccess(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "find-by-name/{name}", method = RequestMethod.GET)
    public Response<OrgUsers> findByName(@PathVariable("name") String name,
                                         @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                         @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(100) Integer pageSize) {
        return orgUsersService.findByName(name, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Org>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                       @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(100) Integer pageSize) {
        return orgService.findAll(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Org> findById(@PathVariable("id") String id) {
        return orgService.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_ENTERPRISE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Org> create(@RequestBody Org request) {
        return orgService.save(request, SecurityUtil.getCurrentAuditor());
    }


    @Secured({ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Org> update(@PathVariable("id") String id, @Valid @RequestBody Org dto) {
        return orgService.update(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_ENTERPRISE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Org> delete(@PathVariable("id") String id) {
        return orgService.delete(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_ADMIN})
    @RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
    public Response<List<OrgUsers>> findOrgUsersById(@PathVariable("id") String id,
                                                     @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(100) Integer pageSize) {
        return orgService.findOrgUsers(id, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    /*@Secured(ROLE_USER)
    @RequestMapping(value = "org-user", method = RequestMethod.POST)
    public Response<OrgUsers> createOrgUser(@RequestBody OrgUsers request) {
        return orgUsersService.save(request, SecurityUtil.getCurrentAuditor());
    }*/

    @Secured({ROLE_ADMIN})
    @RequestMapping(value = "/{branchId}/users/{userId}", method = RequestMethod.PUT)
    public Response<Boolean> updateOrgUser(@PathVariable("branchId") String orgId,
                                           @PathVariable("userId") String userId,
                                           @RequestBody OrgUsers request) {
        return orgService.saveUser(userId, request.getUsers(), request, orgId, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_ADMIN})
    @RequestMapping(value = "/{branchId}/branch-user/{orgUserId}", method = RequestMethod.GET)
    public Response<OrgUsers> findOrgUserById(@PathVariable("branchId") String orgId,
                                              @PathVariable("orgUserId") String orgUserId) {
        return orgService.getUserByOrgUserId(orgUserId, orgId);
    }

    @Secured({ROLE_ADMIN})
    @RequestMapping(value = "/{branchId}/users/add-member", method = RequestMethod.POST)
    public Response<Boolean> findOrgUsersById(@PathVariable("branchId") String orgId, @RequestBody Member member) {
        return orgService.addMember(member, orgId, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_ADMIN})
    @RequestMapping(value = "/{branchId}/users/{userId}/reset-password", method = RequestMethod.POST)
    public Response<Boolean> resetPassword(@PathVariable("branchId") String orgId,
                                           @PathVariable("userId") String userId,
                                           @RequestBody Member member) {
        return orgService.resetPassword(userId, member, orgId, SecurityUtil.getCurrentAuditor(), SecurityUtil.getOrgId());
    }

    @Secured({ROLE_USER})
    @RequestMapping(value = "/login-status", method = RequestMethod.GET)
    public Response<OrgUsers> login() {
        return orgService.getUser(SecurityUtil.getCurrentAuditor(), SecurityUtil.getOrgId());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Response<List<Org>> search(@RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                      @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Min(1) @Max(100) Integer pageSize) {
        return orgService.searchOrg(keyword, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

}
