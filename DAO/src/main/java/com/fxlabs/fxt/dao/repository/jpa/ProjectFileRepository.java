package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface ProjectFileRepository extends JpaRepository<ProjectFile, String> {

    List<ProjectFile> findByProjectId(String projectId);

    Optional<ProjectFile> findByProjectIdAndFilename(String projectId, String fileName);

}
