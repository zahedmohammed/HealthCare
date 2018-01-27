package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl extends GenericServiceImpl<Users, com.fxlabs.fxt.dto.users.Users, String> implements UsersService {

    @Autowired
    public UsersServiceImpl(UsersRepository repository, UsersConverter converter) {
        super(repository, converter);
    }

}
