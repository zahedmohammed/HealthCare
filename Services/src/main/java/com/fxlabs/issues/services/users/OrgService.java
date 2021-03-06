package com.fxlabs.issues.services.users;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.base.UserMinimalDto;
import com.fxlabs.issues.dto.users.Member;
import com.fxlabs.issues.dto.users.Org;
import com.fxlabs.issues.services.base.GenericService;
import com.fxlabs.issues.dto.users.OrgUsers;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgService extends GenericService<Org, String> {

    public Response<Org> findById(String id);

    Response<List<OrgUsers>> findByAccess(String user, Pageable pageable);

    Response<List<Org>> findAll(String user, Pageable pageable);

    Response<Org> delete(String id, String user);

    Response<Org> save(Org dto, String user);

    Response<Org> update(Org dto, String user);

    Response<List<OrgUsers>> findOrgUsers(String org, String user, Pageable pageable);

    Response<Boolean> addMember(Member dto, String orgId, String user);

    Response<Boolean> resetPassword(String id, Member member, String orgId, String user, String auditorOrgId);

    Response<Boolean> saveUser(String id, UserMinimalDto users, OrgUsers orgUser, String orgId, String user);

    Response<OrgUsers> getUser(String id, String orgId);

    Response<OrgUsers> getUserByOrgUserId(String orgUserId, String orgId);

    Response<Org> findByName(String orgName);

    Response<List<Org>> searchOrg(String keyword, String user, Pageable pageable);

    public Response<List<com.fxlabs.issues.dto.users.Org>> findAllOrgs(Pageable pageable);
}
