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

    Optional<Project> findByNameAndInactive(String name, boolean inactive);

    Optional<Project> findByNameIgnoreCaseAndOrgIdAndInactive(String name, String orgId, boolean inactive);

    Optional<Project> findByOrgNameAndNameAndInactive(String orgName, String projectName, boolean inactive);

    Stream<Project> findByProjectTypeAndInactive(ProjectType projectType, boolean inactive);

    Stream<Project> findByInactive(boolean inactive);
}
