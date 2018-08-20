package com.fxlabs.fxt.dto.project;

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
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestSuiteMin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String parent;
    private String name;
    private String description;
    private TestSuiteType type;
    private String endpoint;
    private HttpMethod method;
    private String auth;
    private List<String> headers = new ArrayList<>();
    private List<TestCase> testCases = new ArrayList<>();
    private List<String> assertions = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private List<String> authors = new ArrayList<>();

    private TestSuiteCategory category;
    private TestSuiteSeverity severity;

    private boolean inactive;

    private Policies policies;


}

