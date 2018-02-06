package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface RunService extends GenericService<Run, String> {

    Response<Run> run(String jobId, String region, String tags, String env, String suites);

    public Response<List<TestSuiteResponse>> findByRunId(String runId, Pageable pageable);
}
