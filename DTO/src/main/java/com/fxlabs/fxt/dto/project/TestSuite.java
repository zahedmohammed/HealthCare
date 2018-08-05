package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuite extends BaseDto<String> {

    private ProjectMinimalDto project;
    private String parent;
    private String name;
    private String description;
    private TestSuiteType type;
    private String endpoint;
    private HttpMethod method;
    private String auth;

    private String headersText;
    private List<String> headers = new ArrayList<>();

    private List<TestCase> testCases = new ArrayList<>();

    private String assertionsText;
    private List<String> assertions = new ArrayList<>();

    private String tagsText;
    private List<String> tags = new ArrayList<>();

    private String authorsText;
    private List<String> authors = new ArrayList<>();

    private String initText;
    private List<String> init = new ArrayList<>();

    private String cleanupText;
    private List<String> cleanup = new ArrayList<>();

    private Policies policies;

    private Boolean publishToMarketplace = false;

    private Map<String, String> props = new HashMap<>();

    private TestSuiteCategory category;
    private TestSuiteSeverity severity;

}

