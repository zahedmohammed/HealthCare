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
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AutoCodeGeneratorMatches extends BasicBaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "auto_code_generator_id")
    private AutoCodeGenerator autoCodeGenerator;

    private String name;
    private String value;
    private String methods;
    private String denyRoles;
    private String pathPatterns;
    private String resourceSamples;
    private String queryParams;
    private String bodyProperties;

}

