package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.project.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface IssueRepository extends JpaRepository<Issue, String> {

    Optional<Issue> findByIdAndProjectId(String id, String projectId);

    Page<Issue> findByProjectId(String projectId, Pageable pageable);
}
