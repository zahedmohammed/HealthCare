package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.ProjectFile;
import com.fxlabs.fxt.dao.entity.project.ProjectImports;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectImportsESRepository extends ElasticsearchRepository<ProjectImports, String> {

    Optional<ProjectImports> findByProjectId(String projectId);
}
