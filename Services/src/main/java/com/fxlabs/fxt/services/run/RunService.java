package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.RunSavings;
import com.fxlabs.fxt.dto.run.Suite;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface RunService extends GenericService<Run, String> {

    Response<List<com.fxlabs.fxt.dto.run.Run>> findByJobId(String jobId, String user, Pageable pageable);

    Response<List<com.fxlabs.fxt.dto.run.Run>> findByJobIdForSaving(String jobId, String user, Pageable pageable);

    Response<Run> run(String jobId, String region, String tags, String env, String suites, String categories, String user);

    Response<List<TestSuiteResponse>> runTestSuite(String region, String env, String user, TestSuite dto);

    Response<List<TestSuiteResponse>> findByRunId(String runId, String user, Pageable pageable);

    Response<List<TestSuiteResponse>> findByTestSuite(String jobId, String testSuite, String user, Pageable pageable);

    Response<List<TestSuiteResponse>> findByPk(String id, String name, String user, Pageable pageable);

    Response<List<Suite>> findSummaryByRunId(String runId, String org, Pageable pageable);

//    Response<List<Suite>> search(String runId, String category, String keyword, String org, Pageable pageable);

    Response<List<Suite>> search(String runId, String category, String keyword, String status, String org, Pageable pageable);

    Response<Long> count(String user, Pageable pageable);

    Response<Long> countTests(String user, Pageable pageable);

    Response<Long> countTime(String user, Pageable pageable);

    Response<Long> countBytes(String user, Pageable pageable);

    Response<com.fxlabs.fxt.dto.run.Run> findRunById(String runId, String user);

    Response<com.fxlabs.fxt.dto.run.Run> findRunByJobIdAndRunNo(String jobId,Long runNo,String nav, String user);

    Response<RunSavings> getRunSavings(String runId, String user);

    Response<com.fxlabs.fxt.dto.run.Run> deleteRun(String jobId, Long runId, String user);

    Response<com.fxlabs.fxt.dto.run.Run> reRun(String jobId, Long runId, String user);

    public Response<com.fxlabs.fxt.dto.run.Run> stopRun(String runId, String user);

    Response<Long> countBugs(String orgId,String user,Pageable pageable);
}
