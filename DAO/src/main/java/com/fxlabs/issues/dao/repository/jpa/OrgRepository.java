package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.users.Org;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgRepository extends JpaRepository<Org, String> {

    Optional<Org> findByName(String name);

    Page<Org> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}