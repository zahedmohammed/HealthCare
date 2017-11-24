package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.project.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectEnvironmentRepository extends JpaRepository<Environment, String> {
}
