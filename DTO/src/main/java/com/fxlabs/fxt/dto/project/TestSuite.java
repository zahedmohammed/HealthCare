package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class TestSuite extends BaseDto<String> {

    private String projectId;
    private String name;
    private String description;
    private TestSuiteType type;
    private String endpoint;
    private HttpMethod method;
    private String auth;
    private List<String> headers;
    private List<TestCase> testCases;
    private List<String> assertions;
    private List<String> tags;
    private List<String> authors;

    private List<String> init;
    private List<String> cleanup;

    private Policies policies;

    private Map<String, String> props = new HashMap<>();


}

