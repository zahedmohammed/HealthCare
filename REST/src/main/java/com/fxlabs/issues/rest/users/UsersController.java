package com.fxlabs.issues.rest.users;

import com.fxlabs.issues.dto.base.Message;
import com.fxlabs.issues.dto.base.MessageType;
import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.users.Users;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.fxlabs.issues.rest.base.BaseController.ROLE_USER;
import static com.fxlabs.issues.rest.base.BaseController.USER_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(USER_BASE)
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(
            UsersService usersService) {
        this.usersService = usersService;
    }

    @Secured({ROLE_USER})
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Response<Users> login() {

        if (DDosUtil.hang){
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR,null,"System Unresponsive....  Pls contact Administrator"));
        }

        return usersService.findById(SecurityUtil.getCurrentAuditor());
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

}
