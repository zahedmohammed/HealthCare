package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.ProjectEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectEnvironmentRepository extends JpaRepository<ProjectEnvironment, String> {
}
