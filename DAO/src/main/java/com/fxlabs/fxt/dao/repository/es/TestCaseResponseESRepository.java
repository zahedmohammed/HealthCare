package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.run.TestCaseResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface TestCaseResponseESRepository extends ElasticsearchRepository<TestCaseResponse, String> {

    List<TestCaseResponse> findByProjectAndJobAndSuiteAndTestCase(String project, String job, String suite, String testCase, Pageable pageable);

    List<TestCaseResponse> findByProjectAndJobIdAndSuiteAndTestCase(String project, String jobId, String suite, String testCase, Pageable pageable);

    Stream<TestCaseResponse> findByJobIdAndIssueIdIn(String jobId, List<String> issueId);
}
