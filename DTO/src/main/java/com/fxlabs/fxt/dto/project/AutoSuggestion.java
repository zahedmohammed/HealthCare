package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Mohammed Luqman Shareef
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoSuggestion extends BaseDto<String> {

    private String suggestionId;
    private String projectId;
    private String testSuiteId;
    private String testSuiteName;
    private String testCaseNumber;
    private String region;
    private String respStatusCode;
    private String endPoint;
    private String method;
    private String category;
    private String severity;
    private String issueDesc;
    private String suggestion;
    private String estimates;
    private SuggestionStatus status;
}


