package com.fxlabs.issues.services.users;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.users.OrgUsers;
import com.fxlabs.issues.dto.users.Users;
import com.fxlabs.issues.dto.users.UsersPassword;
import com.fxlabs.issues.services.base.GenericService;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface UsersService extends GenericService<Users, String> {

    public Response<Users> findByEmail(String email);

    public Response<Users> findById(String id);

    public Response<Boolean> personalSignUp(Users users);

    public Response<Boolean> teamSignUp(Users users);

    public Response<Boolean> enterpriseSignUp(Users users);

    public Response<Boolean> addToOrg(OrgUsers orgUsers, String user);

    public Response<UsersPassword> findActivePassword(String email);

    Response<Users> addUser(Users users, List<String> roles);

    Response<Boolean> resetPassword(String id, String password, String confirmPassword);

    public Response<String> generate(String email, String orgId);

}
