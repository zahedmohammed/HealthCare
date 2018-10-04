package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

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
    private String region;
    private String respStatuCode;
    private String endPoint;
    private String method;
    private String category;
    private String issueDesc;
    private String suggestion;
    private String estimates;



}


