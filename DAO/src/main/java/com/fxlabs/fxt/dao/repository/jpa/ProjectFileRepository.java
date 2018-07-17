package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectFileRepository extends JpaRepository<ProjectFile, String> {

    List<ProjectFile> findByProjectId(String projectId);

}
