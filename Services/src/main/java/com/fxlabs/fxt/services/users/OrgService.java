package com.fxlabs.fxt.services.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.base.UserMinimalDto;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.users.Member;
import com.fxlabs.fxt.dto.users.Org;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgService extends GenericService<Org, String> {

    Response<List<com.fxlabs.fxt.dto.users.OrgUsers>> findByAccess(String user, Pageable pageable);

    Response<List<Org>> findAll(String user, Pageable pageable);

    Response<Org> delete(String id, String user);

    Response<Org> save(Org dto, String user);

    Response<com.fxlabs.fxt.dto.users.Org> update(com.fxlabs.fxt.dto.users.Org dto, String user);

    Response<List<com.fxlabs.fxt.dto.users.OrgUsers>> findOrgUsers(String org, String user, Pageable pageable);

    Response<Boolean> addMember(Member dto, String orgId, String user);

    Response<Boolean> resetPassword(String id, Member member, String orgId, String user);

    Response<Boolean> saveUser(String id, UserMinimalDto users, com.fxlabs.fxt.dto.users.OrgUsers orgUser, String orgId, String user);

    Response<com.fxlabs.fxt.dto.users.OrgUsers> getUser(String id, String orgId);

    Response<com.fxlabs.fxt.dto.users.OrgUsers> getUserByOrgUserId(String orgUserId, String orgId);

    Response<Org> findByName(String orgName);
}
