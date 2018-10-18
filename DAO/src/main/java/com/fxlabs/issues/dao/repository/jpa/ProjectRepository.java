package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByNameAndInactive(String name, boolean inactive);

    Optional<Project> findByIdAndOrgId(String id, String org);

    List<Project> findByAccountIdAndInactive(String id, boolean inactive);

    Page<Project> findByOrgIdAndInactive(String org, boolean inactive, Pageable pageable);
    List<Project> findByOrgIdAndInactive(String org, boolean inactive);

    Long countByOrgIdAndInactive(String org, boolean inactive);

    Optional<Project> findByNameIgnoreCaseAndOrgIdAndInactive(String name, String orgId, boolean inactive);

    Optional<Project> findByNameIgnoreCaseAndIdNotLikeAndOrgIdAndInactive(String name, String id, String orgId, boolean inactive);

    Optional<Project> findByOrgNameAndNameAndInactive(String orgName, String projectName, boolean inactive);

    Stream<Project> findByInactive(boolean inactive);

    Page<Project> findByOrgIdAndNameContainingIgnoreCaseAndInactive(String org, String keyword,boolean inactive, Pageable pageable);
}
