package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.ProjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByNameAndInactive(String name, boolean inactive);

    Optional<Project> findByIdAndOrgId(String id, String org);

    Page<Project> findByOrgIdAndInactive(String org, boolean inactive, Pageable pageable);

    Optional<Project> findByNameIgnoreCaseAndOrgIdAndInactive(String name, String orgId, boolean inactive);

    Optional<Project> findByOrgNameAndNameAndInactive(String orgName, String projectName, boolean inactive);

    Stream<Project> findByProjectTypeAndInactive(ProjectType projectType, boolean inactive);

    Stream<Project> findByInactive(boolean inactive);
}
