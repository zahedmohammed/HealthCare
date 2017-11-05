package com.fxlabs.fxt.rest;

import com.fxlabs.fxt.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping("/users")
    public Iterable findAll() {
        return usersService.findAll();
    }

    @PostMapping("/users")
    public void create() {
        usersService.create("test" + new Date());
    }

}
