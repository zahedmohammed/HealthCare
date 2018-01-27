package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.converters.users.UsersConverter;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.users.Users;
import com.fxlabs.fxt.dao.repository.jpa.UsersRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsersServiceImpl extends GenericServiceImpl<Users, com.fxlabs.fxt.dto.users.Users, String> implements UsersService {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository repository, UsersConverter converter, PasswordEncoder passwordEncoder) {
        super(repository, converter);
        this.passwordEncoder = passwordEncoder;
    }


    public Response<com.fxlabs.fxt.dto.users.Users> findByEmail(String email) {
        Optional<Users> usersOptional = ((UsersRepository) repository).findByEmail(email);

        if (usersOptional.isPresent()) {
            return new Response<com.fxlabs.fxt.dto.users.Users>(converter.convertToDto(usersOptional.get()));
        }
        return new Response<Project>().withErrors(true).withMessage(new Message(MessageType.ERROR, "", String.format("Invalid email or password")));
    }

}
