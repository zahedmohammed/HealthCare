package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.ProjectApiEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectAPIEndpointRepository extends JpaRepository<ProjectApiEndpoint, String> {
}
