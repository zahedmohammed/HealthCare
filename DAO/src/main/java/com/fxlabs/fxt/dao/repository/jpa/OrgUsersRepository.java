package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgUsersRepository extends JpaRepository<OrgUsers, String> {

    Optional<OrgUsers> findByOrgIdAndUsersIdAndStatus(String orgId, String usersId, OrgUserStatus status);

    Optional<OrgUsers> findByOrgIdAndUsersId(String orgId, String usersId);

    Optional<OrgUsers> findByOrgIdAndUsersIdAndOrgRole(String orgId, String usersId, OrgRole orgRole);

    Set<OrgUsers> findByUsersIdAndStatusAndOrgRole(String usersId, OrgUserStatus status, OrgRole orgRole);

    Page<OrgUsers> findByOrgId(String usersId, Pageable pageable);

    Page<OrgUsers> findByUsersIdAndStatusAndOrgRole(String usersId, OrgUserStatus status, OrgRole orgRole, Pageable pageable);

    Optional<OrgUsers> findByUsersIdAndStatusAndOrgNameAndOrgRole(String usersId, OrgUserStatus status, String orgName, OrgRole orgRole);

    Optional<OrgUsers> findByUsersIdAndOrgIdAndStatusAndOrgRole(String usersId, String orgId, OrgUserStatus status, OrgRole orgRole);
}
