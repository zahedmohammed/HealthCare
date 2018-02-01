package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgUsersRepository extends JpaRepository<OrgUsers, String> {
}
