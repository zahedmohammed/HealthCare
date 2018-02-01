package com.fxlabs.fxt.rest.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fxlabs.fxt.rest.base.BaseController.USER_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(USER_BASE)
public class UsersController extends BaseController<Users, String> {

    @Autowired
    public UsersController(
            UsersService usersService) {
        super(usersService);
    }

    @CrossOrigin
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public Response<Boolean> run(@RequestBody Users users) {
        return ((UsersService) service).signUp(users);
    }

}
