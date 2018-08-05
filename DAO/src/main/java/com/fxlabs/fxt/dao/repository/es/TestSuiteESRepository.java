package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteESRepository extends ElasticsearchRepository<TestSuite, String> {

    //Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);

    Long countByProjectIdAndTypeAndTagsIn(String projectId, String type, List<String> tags);

    Long countByProjectIdAndType(String projectId, String type);

    Long countByProjectId(String projectId);

    Page<TestSuite> findByPublishToMarketplace(Boolean isPublished, Pageable pageable);

    Page<TestSuite> findByPublishToMarketplaceAndNameStartsWithIgnoreCase(Boolean isPublished, String name, Pageable pageable);

    Stream<TestSuite> findByProjectIdAndTypeAndTagsIn(String projectId, String type, List<String> tags);
    Page<TestSuite> findByProjectId(String projectId, Pageable pageable);

    Stream<TestSuite> findByProjectIdAndType(String projectId, String type);

    Stream<TestSuite> findByProjectIdAndNameIn(String projectId, List<String> name);

    Optional<TestSuite> findByProjectIdAndName(String projectId, String name);
}
