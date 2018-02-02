package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.ProjectFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectFileESRepository extends ElasticsearchRepository<ProjectFile, String> {

    Optional<ProjectFile> findByProjectIdAndFilenameIgnoreCase(String projectId, String fileName);

    List<ProjectFile> findByProjectId(String projectId, org.springframework.data.domain.Pageable pageable);
}
