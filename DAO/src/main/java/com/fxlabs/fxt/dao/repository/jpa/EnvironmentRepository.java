package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface EnvironmentRepository extends JpaRepository<Environment, String> {

    List<Environment> findByProjectId(String projectId);

    Long countByProjectIdAndInactive(String project, boolean inactive);
}
