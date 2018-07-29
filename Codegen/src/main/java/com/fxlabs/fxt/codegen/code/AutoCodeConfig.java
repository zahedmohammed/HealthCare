package com.fxlabs.fxt.codegen.code;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dto.project.RequestMapping;
import com.fxlabs.fxt.dto.project.ResourceSample;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammed Luqman Shareef
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoCodeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

//    private List<Database> databases;

//    private List<String> logForgingPatterns;

//    private List<TestSuite> testSuites;
    private List<Generator> generators;

    private String genPolicy;
    private String openAPISpec;

    private Map<String, String> propertyMapping;

//    private List<RequestMapping> requestMappings = new ArrayList<>();
    private List<ResourceSample> resourceSamples = new ArrayList<>();





}


