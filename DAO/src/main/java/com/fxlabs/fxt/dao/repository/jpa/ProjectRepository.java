package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByNameAndCreatedBy(String name, String createdBy);

    Optional<Project> findByNameIgnoreCaseAndOrgIdAndDeleted(String name, String orgId, boolean deleted);

    Stream<Project> findByProjectTypeAndDeleted(ProjectType projectType, boolean deleted);
}
