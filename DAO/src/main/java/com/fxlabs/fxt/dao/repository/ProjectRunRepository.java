package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.ProjectRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRunRepository extends JpaRepository<ProjectRun, String> {
}
