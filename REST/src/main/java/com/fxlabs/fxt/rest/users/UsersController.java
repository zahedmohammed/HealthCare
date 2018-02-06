package com.fxlabs.fxt.rest.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Org;
import com.fxlabs.fxt.dto.users.OrgUsers;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(USER_BASE)
public class UsersController /*extends BaseController<Users, String>*/ {

    private UsersService usersService;

    @Autowired
    public UsersController(
            UsersService usersService) {
        this.usersService = usersService;
    }

    @CrossOrigin
    @RequestMapping(value = "/personal-sign-up", method = RequestMethod.POST)
    public Response<Boolean> personalSignUp(@RequestBody Users users) {
        return usersService.personalSignUp(users);
    }

    @CrossOrigin
    @RequestMapping(value = "/team-sign-up", method = RequestMethod.POST)
    public Response<Boolean> teamSignUp(@RequestBody Users users) {
        return usersService.teamSignUp(users);
    }

    @CrossOrigin
    @RequestMapping(value = "/enterprise-sign-up", method = RequestMethod.POST)
    public Response<Boolean> enterpriseSignUp(@RequestBody Users users) {
        return usersService.enterpriseSignUp(users);
    }

    @Secured({ROLE_ADMIN, ROLE_ENTERPRISE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Users>> findUsers() {
        return null;
    }

    @Secured({ROLE_USER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Users>> upgrade(Org org) {
        return null;
    }

    @Secured({ROLE_ADMIN, ROLE_ENTERPRISE_ADMIN})
    @RequestMapping(value = "/org-add", method = RequestMethod.POST)
    public Response<Boolean> addToOrg(@RequestBody OrgUsers orgUsers) {
        return usersService.addToOrg(orgUsers, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_ADMIN, ROLE_ENTERPRISE_ADMIN})
    @RequestMapping(value = "/org-remove", method = RequestMethod.POST)
    public Response<Boolean> removeFromOrg(@RequestBody OrgUsers orgUsers) {
        return null;
    }

}
