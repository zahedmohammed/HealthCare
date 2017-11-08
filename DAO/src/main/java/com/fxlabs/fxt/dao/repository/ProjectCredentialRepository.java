package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.ProjectCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCredentialRepository extends JpaRepository<ProjectCredential, String> {
}
