package com.fxlabs.fxt.dao.entity.project.autocode;

import com.fxlabs.fxt.dao.entity.base.BasicBaseEntity;
import com.fxlabs.fxt.dao.entity.project.TestSuiteSeverity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mohammed Luqman Shareef
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AutoCodeGeneratorMatches {

    private String name;
    private String value;
    private String methods;
    private String denyRoles;
    private String allowRoles;
    private String pathPatterns;
    private String resourceSamples;
    private String queryParams;
    private String bodyProperties;

}

