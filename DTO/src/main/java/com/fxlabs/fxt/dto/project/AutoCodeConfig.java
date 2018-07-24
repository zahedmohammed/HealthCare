package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammed Luqman Shareef
 * @author Mohammed Shoukath Ali
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoCodeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private GenPolicy genPolicy;
    private String openAPISpec;

    private List<Database> databases;

    private List<String> logForgingPatterns;

    private List<TestSuite> testSuites;

    private Map<String, String> propertyMapping;

}


