package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dto.base.BasicBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author Mohammed Shoukath Ali
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoCodeGenerator extends BasicBaseDto {

    private String type;
    private List<String> assertions;
    private String assertionsText;
    private TestSuiteSeverity severity;
    private Database database;
    protected boolean inactive = false;
    private List<AutoCodeGeneratorMatches> autoCodeGeneratorMatches;

}

