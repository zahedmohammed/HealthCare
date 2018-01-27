package com.fxlabs.fxt;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FxUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    public FxUserDetailsService() {
        super();
    }

    // API

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final Response<Users> usersResponse = usersService.findByEmail(username);
        if (usersResponse.isErrors()) {
            throw new UsernameNotFoundException(username);
        }
        return new FxUserPrinciple(usersResponse.getData());
    }
}
