package com.fxlabs.fxt.rest;

import static com.fxlabs.fxt.rest.BaseController.USER_BASE;

import com.fxlabs.fxt.dto.Users;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.users.UsersService;
import com.fxlabs.fxt.services.users.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController(USER_BASE)
public class UsersController extends BaseController {

    @Autowired
    UsersService usersService;

    @GetMapping()
    public Response<List<Users>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                         @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return usersService.findAll(null, new PageRequest(0, 20));
    }

    @PostMapping()
    public void create(Users users) {
        usersService.save(users);
    }


}
