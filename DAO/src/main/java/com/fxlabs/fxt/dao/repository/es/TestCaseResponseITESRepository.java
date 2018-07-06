package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface TestCaseResponseITESRepository extends ElasticsearchRepository<TestCaseResponseIssueTracker, String> {

    Optional<TestCaseResponseIssueTracker> findByTestCaseResponseIssueTrackerId(String testCaseResponseIssueTrackerId);
}
