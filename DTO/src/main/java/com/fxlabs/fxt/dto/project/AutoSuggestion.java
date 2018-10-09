package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class AutoSuggestion implements Serializable{

    private String projectId;
    private String testSuiteName;
    private String testCaseNumber;
    private String region;
    private String respStatusCode;
    private String endPoint;
    private String method;
    private String category;
    private String issueDesc;
    private String suggestion;
    private String estimates;
    private Date createdDate;
}


