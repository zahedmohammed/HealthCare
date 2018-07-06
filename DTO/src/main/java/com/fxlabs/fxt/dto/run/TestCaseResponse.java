package com.fxlabs.fxt.dto.run;

import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestCaseResponse extends BaseDto<String> {

    private String project;
    private String job;
    private String jobId;
    private String env;
    private String region;
    private String suite;
    private String testCase;
    private String endpoint;
    private String endpointEval;
    private String request;
    private String requestEval;
    private String response;
    private String logs;
    private String itKey;
    private String issueId;
    private String result; // pass/fail
    private String statusCode;
    private String headers;
    private Long time;
    private Integer size;
    private String issueTrackerHost;
    private String issueTrackerProjectName;
    private String username;
    private String password;
    private String runId;



}

