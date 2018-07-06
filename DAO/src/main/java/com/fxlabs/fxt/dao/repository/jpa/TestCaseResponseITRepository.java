package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface TestCaseResponseITRepository extends JpaRepository<TestCaseResponseIssueTracker, String> {

    Optional<TestCaseResponseIssueTracker> findByTestCaseResponseIssueTrackerId(String testCaseResponseIssueTrackerId);

}
