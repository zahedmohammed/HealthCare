package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fxlabs.fxt.dto.base.BasicBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Mohammed Shoukath Ali
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoCodeGeneratorMatches extends BasicBaseDto {

    private String name;
    private String value;
    private String methods;
    private String denyRoles;
    private String pathPatterns;
    private String resourceSamples;
    private String queryParams;
    private String bodyProperties;

}
