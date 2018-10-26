package com.fxlabs.issues.dao.repository.jpa;

import com.fxlabs.issues.dao.entity.project.Issue;
import com.fxlabs.issues.dao.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface IssueRepository extends JpaRepository<Issue, String> {

    Optional<Issue> findByIdAndProjectId(String id, String projectId);

    Page<Issue> findByProjectId(String projectId, Pageable pageable);
}
