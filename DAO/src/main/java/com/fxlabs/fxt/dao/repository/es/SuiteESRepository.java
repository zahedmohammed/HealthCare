package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.project.TestSuiteCategory;
import com.fxlabs.fxt.dao.entity.project.TestSuiteSeverity;
import com.fxlabs.fxt.dao.entity.run.Suite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface SuiteESRepository extends ElasticsearchRepository<Suite, String> {

    Page<Suite> findByRunId(String runId, Pageable pageable);

    Page<Suite> findByRunIdAndCategoryAndSuiteNameStartingWithIgnoreCase(String runId, TestSuiteCategory category, String keyword, Pageable pageable);

    Page<Suite> findByRunIdAndCategoryAndSuiteNameContainingIgnoreCase(String runId, TestSuiteCategory category, String keyword, Pageable pageable);

    Page<Suite> findByRunIdAndCategoryAndFailedGreaterThan(String runId, TestSuiteCategory category, Long failed , Pageable pageable);

    Page<Suite> findByRunIdAndSuiteNameStartingWithIgnoreCaseAndFailedGreaterThan(String runId, String keyword, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndSuiteNameContainingIgnoreCaseAndFailedGreaterThan(String runId, String keyword, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndCategoryAndFailed(String runId, TestSuiteCategory category, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndCategoryAndSuiteNameStartingWithIgnoreCaseAndFailedGreaterThan(String runId, TestSuiteCategory category, String keyword, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndCategoryAndSuiteNameContainingIgnoreCaseAndFailedGreaterThan(String runId, TestSuiteCategory category, String keyword, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndCategoryAndSuiteNameStartingWithIgnoreCaseAndFailed(String runId, TestSuiteCategory category, String keyword, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndCategoryAndSuiteNameContainingIgnoreCaseAndFailed(String runId, TestSuiteCategory category, String keyword, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndCategory(String runId, TestSuiteCategory category, Pageable pageable);

    Page<Suite> findByRunIdAndFailedGreaterThan(String runId, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndSuiteNameContainingIgnoreCase(String runId, String keyword, Pageable pageable);

    Page<Suite> findByRunIdAndFailed(String runId, Long failed, Pageable pageable);

    Optional<Suite> findById(String id);

    Page<Suite> findByRunIdAndSuiteNameStartingWithIgnoreCaseAndFailed(String runId, String keyword, Long failed, Pageable pageable);

    Page<Suite> findByRunIdAndSuiteNameContainingIgnoreCaseAndFailed(String runId, String keyword, Long failed, Pageable pageable);
}
