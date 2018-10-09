package com.fxlabs.fxt.dto.it;

import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestCaseResponseIssueTracker extends BaseDto<String> {

    private String projectId;
    private String jobId;
    private String testSuiteName;
    private String testCaseNumber;

    private String issueId;
    private Integer validations;
    private String status;
    private String runId;
}
