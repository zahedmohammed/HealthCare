package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.ProjectAPIEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectAPIEndpointRepository extends JpaRepository<ProjectAPIEndpoint, String> {
}
