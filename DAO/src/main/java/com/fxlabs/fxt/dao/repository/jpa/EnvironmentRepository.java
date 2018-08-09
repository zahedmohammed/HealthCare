package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface EnvironmentRepository extends JpaRepository<Environment, String> {

    List<Environment> findByProjectIdAndInactive(String projectId, boolean inactive);

    Optional<Environment> findByIdAndInactive(String id, boolean inactive);

    Long countByProjectIdAndInactive(String project, boolean inactive);
}
