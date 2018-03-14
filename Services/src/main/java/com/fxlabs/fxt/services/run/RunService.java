package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.Suite;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface RunService extends GenericService<Run, String> {

    Response<List<com.fxlabs.fxt.dto.run.Run>> findByJobId(String jobId, String user, Pageable pageable);

    Response<Run> run(String jobId, String region, String tags, String env, String suites, String user);

    Response<List<TestSuiteResponse>> findByRunId(String runId, String user, Pageable pageable);

    Response<List<Suite>> findSummaryByRunId(String runId, String user, Pageable pageable);

    Response<Long> count(String user, Pageable pageable);

    Response<Long> countTests(String user, Pageable pageable);

    Response<Long> countTime(String user, Pageable pageable);

    Response<Long> countBytes(String user, Pageable pageable);
}
