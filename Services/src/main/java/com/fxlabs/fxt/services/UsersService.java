package com.fxlabs.fxt.services;

import com.fxlabs.fxt.converters.UsersConverter;
import com.fxlabs.fxt.dao.entity.Users;
import com.fxlabs.fxt.dao.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersConverter converter;


    public void create(String name) {
        Users users = new Users();
        users.setName(name);

        usersRepository.save(users);

        com.fxlabs.fxt.dto.Users dto = converter.convertToDto(users);
        System.out.println("DTO ---> " + dto.toString());
    }

    public Iterable<Users> findAll() {
        return usersRepository.findAll();
    }
}
