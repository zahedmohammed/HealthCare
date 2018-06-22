package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteResponseRepository extends JpaRepository<TestSuiteResponse, String> {

    Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);

    Page<TestSuiteResponse> findByTestSuite(String testSuite, Pageable pageable);

    Page<TestSuiteResponse> findByRunIdAndTestSuite(String id, String name, Pageable pageable);
}
