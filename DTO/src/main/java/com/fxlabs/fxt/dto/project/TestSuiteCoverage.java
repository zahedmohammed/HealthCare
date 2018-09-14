package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammed Luqman Shareef
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuiteCoverage implements Serializable {

//    private ProjectMinimalDto project;

    private Long totalEndpoints;
    private Long totalSuites;
    private Long totalTestCases;


    private List<Endpoint> endpoints = new ArrayList<>();
    private List<TestSuiteCount> countByMethod = new ArrayList<>();
    private List<TestSuiteCount> countByCategory = new ArrayList<>();
    private List<TestSuiteCount> countBySeverity = new ArrayList<>();


//    private Map<String, Map<String, Long>> countByMethod = new HashMap<>();
//    private Map<String, Map<String, Long>> countByCategory = new HashMap<>();
//    private Map<String, Map<String, Long>> countBySeverity = new HashMap<>();

//    private List<TestSuiteCoverageDetails> coverageDetails = new ArrayList<>();

}

