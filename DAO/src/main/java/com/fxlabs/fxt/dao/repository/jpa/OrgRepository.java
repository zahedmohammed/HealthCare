package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.users.Org;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgRepository extends JpaRepository<Org, String> {

    Optional<Org> findByName(String name);
}
