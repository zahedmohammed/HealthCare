package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.ProjectDataSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDataSetRepository extends JpaRepository<ProjectDataSet, String> {

    List<ProjectDataSet> findByProjectId(String projectId);

    Long countByProjectId(String projectId);
}
