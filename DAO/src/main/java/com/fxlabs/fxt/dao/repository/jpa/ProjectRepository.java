package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByNameAndCreatedBy(String name, String createdBy);

    Optional<Project> findByNameIgnoreCaseAndOrgId(String name, String orgId);
}
