package com.fxlabs.fxt.rest.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.fxlabs.fxt.rest.base.BaseController.USER_BASE;

@RestController
@RequestMapping(USER_BASE)
public class UsersController extends BaseController<Users, String> {

    @Autowired
    public UsersController(
            UsersService usersService) {
        super(usersService);
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public Response<Boolean> run(@RequestBody Users users) {
        return ((UsersService) service).signUp(users);
    }

}
