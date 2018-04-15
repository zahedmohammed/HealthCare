package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.dto.users.UsersPassword;
import com.fxlabs.fxt.services.base.GenericService;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface UsersService extends GenericService<Users, String> {

    public Response<Users> findByEmail(String email);

    public Response<Users> findById(String id);

    public Response<Boolean> personalSignUp(com.fxlabs.fxt.dto.users.Users users);

    public Response<Boolean> teamSignUp(com.fxlabs.fxt.dto.users.Users users);

    public Response<Boolean> enterpriseSignUp(com.fxlabs.fxt.dto.users.Users users);

    public Response<Boolean> addToOrg(com.fxlabs.fxt.dto.users.OrgUsers orgUsers, String user);

    public Response<UsersPassword> findActivePassword(String email);

    Response<Users> addUser(com.fxlabs.fxt.dto.users.Users users, List<String> roles);

    Response<Boolean> resetPassword(String id, String password, String confirmPassword);

}
