package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.ProjectJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJobRepository extends JpaRepository<ProjectJob, String> {
}
