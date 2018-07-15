package com.fxlabs.fxt.codegen.code;

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
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoCodeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Database> databases;

    private List<String> logForgingPatterns;

    private List<TestSuite> testSuites;

    private Map<String, String> propertyMapping;

}


