package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface TestCaseResponseITESRepository extends ElasticsearchRepository<TestCaseResponseIssueTracker, String> {

    Optional<TestCaseResponseIssueTracker> findByTestCaseResponseIssueTrackerId(String testCaseResponseIssueTrackerId);

    Page<TestCaseResponseIssueTracker> findByJobIdAndStatus(String id, String status, Pageable pageable);

    Page<TestCaseResponseIssueTracker> findByJobId(String id, Pageable pageable);

    Page<TestCaseResponseIssueTracker> findByProjectIdAndStatus(String id, String status, Pageable pageable);

    Page<TestCaseResponseIssueTracker> findByProjectId(String id, Pageable pageable);

    Page<TestCaseResponseIssueTracker> findByJobIdInAndStatus(List<String> jobIds, String status, Pageable pageable);

    Page<TestCaseResponseIssueTracker> findByJobIdIn(List<String> jobIds, Pageable pageable);
}
