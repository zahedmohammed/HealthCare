package com.fxlabs.fxt.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @author Mohammed Shoukath Ali
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoCodeConfigMinimal implements Serializable {

    private static final long serialVersionUID = 1L;

    private GenPolicy genPolicy;

    private String openAPISpec;

    private List<AutoCodeGeneratorMinimal> generators;

}


