package com.fxlabs.fxt.rest.users;

import static com.fxlabs.fxt.rest.base.BaseController.USER_BASE;

import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(USER_BASE)
public class UsersController extends BaseController<Users> {

    @Autowired
    public UsersController(
            UsersService usersService) {
        super(usersService);
    }

}
