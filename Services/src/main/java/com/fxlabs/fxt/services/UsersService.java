package com.fxlabs.fxt.services;

import com.fxlabs.fxt.converters.UsersConverter;
import com.fxlabs.fxt.dao.entity.Users;
import com.fxlabs.fxt.dao.repository.UsersRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersService extends GenericServiceImpl<Users, com.fxlabs.fxt.dto.Users, String> {

    @Autowired
    public UsersService(UsersRepository repository, UsersConverter converter) {
        super(repository, converter);
    }


    public void create(String name) {
        Users users = new Users();
        users.setName(name);

        repository.save(users);

        com.fxlabs.fxt.dto.Users dto = converter.convertToDto(users);
        logger.info("DTO ---> [{}]", dto.toString());
    }

    public Iterable<Users> findAll() {
        return repository.findAll();
    }
}
