package com.fxlabs.issues.dao.repository.es;

import com.fxlabs.issues.dao.entity.project.Issue;
import com.fxlabs.issues.dao.entity.project.Project;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface IssueESRepository extends ElasticsearchRepository<Issue, String> {

    Optional<Project> findByIdAndProjectId(String id, String projectId);
}
