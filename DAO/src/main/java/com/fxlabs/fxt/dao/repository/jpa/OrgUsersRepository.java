package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgUsersRepository extends JpaRepository<OrgUsers, String> {

    Optional<OrgUsers> findByOrgIdAndUsersIdAndStatus(String orgId, String usersId, OrgUserStatus status);

    Set<OrgUsers> findByUsersIdAndStatusAndOrgRole(String usersId, OrgUserStatus status, OrgRole orgRole);
}
