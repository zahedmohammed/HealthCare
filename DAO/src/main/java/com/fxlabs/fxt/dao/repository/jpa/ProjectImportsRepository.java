package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.ProjectFile;
import com.fxlabs.fxt.dao.entity.project.ProjectImports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectImportsRepository extends JpaRepository<ProjectImports, String> {

    Optional<ProjectImports> findByProjectId(String projectId);

}
