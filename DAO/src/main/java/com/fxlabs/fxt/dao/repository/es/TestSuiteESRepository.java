package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteESRepository extends ElasticsearchRepository<TestSuite, String> {

    //Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);

    Long countByProjectIdAndTypeAndTagsIn(String projectId, String type, List<String> tags);

    Long countByProjectIdAndType(String projectId, String type);

    Long countByProjectId(String projectId);

    Stream<TestSuite> findByProjectIdAndTypeAndTagsIn(String projectId, String type, List<String> tags);

    Stream<TestSuite> findByProjectIdAndType(String projectId, String type);

    Stream<TestSuite> findByProjectIdAndNameIn(String projectId, List<String> name);
}