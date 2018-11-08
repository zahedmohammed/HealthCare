package com.fxlabs.issues.dto.project;

import com.fxlabs.issues.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Issue extends BaseDto<String> {


    private String endpoint;

    private HttpMethod method;

    private List<String> headers;

    private String requestBody;

    private String responseBody;

    private String responseHeaders;

    private String assertions;

    private String statusCode;

    private String failedAssertions;

    private String result;

    private String description;

    private Project project;

    private List<String> tags;

    private String env;

    private String issueName;

    private IssueType issueType;

    private IssueStatus issueStatus;

    private String assignedTo;
}

