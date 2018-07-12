package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteResponseRepository extends JpaRepository<TestSuiteResponse, String> {

    Page<TestSuiteResponse> findByRunId(String id, Pageable pageable);

    List<TestSuiteResponse> findByRunId(String id);

    Page<TestSuiteResponse> findByTestSuiteAndRunIdIn(String testSuite, List<String> runIds, Pageable pageable);

    Page<TestSuiteResponse> findByRunIdAndTestSuite(String id, String name, Pageable pageable);
}
