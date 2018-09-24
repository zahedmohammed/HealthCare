package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class APICoverageDetails implements Serializable {

    private Long totalEndpoints;
    private Long totalSuites;
    private Long totalTestCases;

    private List<Endpoint> endpoints = new ArrayList<>();

    private List<TestSuiteCount> countByMethod = new ArrayList<>();
    private List<TestSuiteCount> countByCategory = new ArrayList<>();
    private List<TestSuiteCount> countBySeverity = new ArrayList<>();

    private List<Coverage> coverage = new ArrayList<>();

}

