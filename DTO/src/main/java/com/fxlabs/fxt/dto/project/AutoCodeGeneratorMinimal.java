package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dto.base.BasicBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoCodeGeneratorMinimal implements Serializable{

    private String type;
    private List<String> assertions;
    private TestSuiteSeverity severity;
    private Database database;
    protected boolean inactive = false;
    private List<AutoCodeGeneratorMatchesMinimal> matches;

}

