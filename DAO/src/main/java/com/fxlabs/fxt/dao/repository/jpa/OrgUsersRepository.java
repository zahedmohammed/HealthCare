package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.OrgRole;
import com.fxlabs.fxt.dao.entity.users.OrgUserStatus;
import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgUsersRepository extends JpaRepository<OrgUsers, String> {

    Optional<OrgUsers> findByOrgIdAndUsersIdAndStatus(String orgId, String usersId, OrgUserStatus status);

    Optional<OrgUsers> findByOrgIdAndUsersId(String orgId, String usersId);

    Optional<OrgUsers> findByOrgIdAndUsersIdAndOrgRole(String orgId, String usersId, OrgRole orgRole);

    Set<OrgUsers> findByUsersIdAndStatusAndOrgRoleIn(String usersId, OrgUserStatus status, Collection<OrgRole> orgRoles);

    Set<OrgUsers> findByUsersIdAndStatusAndOrgRoleInAndOrgNameContaining(String usersId, OrgUserStatus status, Collection<OrgRole> orgRoles, String keyword);

    Page<OrgUsers> findByOrgId(String usersId, Pageable pageable);

    Page<OrgUsers> findByUsersIdAndStatusAndOrgRoleIn(String usersId, OrgUserStatus status, Collection<OrgRole> orgRoles, Pageable pageable);

    Optional<OrgUsers> findByOrgNameAndUsersIdAndStatus(String name, String usersId, OrgUserStatus status);

    Optional<OrgUsers> findByUsersIdAndStatusAndOrgNameAndOrgRole(String usersId, OrgUserStatus status, String orgName, OrgRole orgRole);

    Optional<OrgUsers> findByUsersIdAndStatusAndOrgName(String usersId, OrgUserStatus status, String orgName);

    List<OrgUsers> findByUsersIdAndStatus(String usersId, OrgUserStatus status, Pageable pageable);

    Optional<OrgUsers> findByUsersIdAndOrgIdAndStatusAndOrgRole(String usersId, String orgId, OrgUserStatus status, OrgRole orgRole);
}
