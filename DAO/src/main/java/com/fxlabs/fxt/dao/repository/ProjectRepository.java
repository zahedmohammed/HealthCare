package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByNameAndCreatedBy(String name, String createdBy);
}
