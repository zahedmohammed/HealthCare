package com.fxlabs.fxt.services.run;

import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.services.base.GenericService;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteResponseService extends GenericService<TestSuiteResponse, String> {

    public Long passedSum(String runId);

    public Long failedSum(String runId);

    public Long timeSum(String runId);
}
