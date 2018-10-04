package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.it.TestCaseResponseIssueTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface TestCaseResponseITRepository extends JpaRepository<TestCaseResponseIssueTracker, String> {

   // Optional<TestCaseResponseIssueTracker> findByTestCaseResponseIssueTrackerId(String testCaseResponseIssueTrackerId);

  //  List<TestCaseResponseIssueTracker> findByRunIdAndTestCaseResponseIssueTrackerIdLike(String runId, String itId);

    //Long countByStatusAndTestCaseResponseIssueTrackerIdLike(String status, String itId);

    //@Query("SELECT SUM(iT.validations) FROM TestCaseResponseIssueTracker iT WHERE iT.testCaseResponseIssueTrackerId LIKE ?1 and iT.modifiedDate>?2")
  //  Long findSumByTestCaseResponseIssueTrackerIdLike(String itId, Date modified_date);

    Optional<TestCaseResponseIssueTracker> findByProjectIdAndJobIdAndTestSuiteNameAndTestCaseNumber(String projectId, String jobId,String testSuite,String testCase);

    List<TestCaseResponseIssueTracker> findByRunIdAndProjectIdAndJobId(String runId, String projectId, String jobId);

    Stream<TestCaseResponseIssueTracker> findByJobIdAndStatusIgnoreCase(String jobId, String status);

    long countByStatusIgnoreCaseAndProjectIdAndJobId(String open, String name, String id);

    @Query("SELECT SUM(iT.validations) FROM TestCaseResponseIssueTracker iT WHERE iT.projectId=?1 and iT.jobId=?2 and iT.modifiedDate>?3")
    Long sumByProjectIdAndJobIdAndModifiedDate(String projectId, String jobId, Date currentMonthStartDate);

    @Query("SELECT SUM(iT.validations) FROM TestCaseResponseIssueTracker iT WHERE iT.projectId=?1 and iT.jobId=?2 and iT.modifiedDate>=?3 and iT.modifiedDate<=?4")
    Long sumByProjectIdAndJobIdAndModifiedDateGreaterThanAndCreatedDateLessThanEqual(String id, String id1, Date fromDate, Date toDate);
}
