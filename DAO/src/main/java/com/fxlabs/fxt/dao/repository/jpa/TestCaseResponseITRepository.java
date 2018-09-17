package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface TestCaseResponseITRepository extends JpaRepository<TestCaseResponseIssueTracker, String> {

    Optional<TestCaseResponseIssueTracker> findByTestCaseResponseIssueTrackerId(String testCaseResponseIssueTrackerId);

    List<TestCaseResponseIssueTracker> findByRunIdAndTestCaseResponseIssueTrackerIdLike(String runId, String itId);

    Long countByStatusAndTestCaseResponseIssueTrackerIdLike(String status, String itId);

    @Query("SELECT SUM(iT.validations) FROM TestCaseResponseIssueTracker iT WHERE iT.testCaseResponseIssueTrackerId LIKE ?1")
    Long findSumByTestCaseResponseIssueTrackerIdLike(String itId);

}
