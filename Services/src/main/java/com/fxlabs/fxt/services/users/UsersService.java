package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.services.base.GenericService;

public interface UsersService extends GenericService<Users, String> {

    public Response<Users> findByEmail(String email);

}
