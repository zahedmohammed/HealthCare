package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}
