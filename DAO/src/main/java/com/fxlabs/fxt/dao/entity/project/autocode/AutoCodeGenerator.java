package com.fxlabs.fxt.dao.entity.project.autocode;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.base.BasicBaseEntity;
import com.fxlabs.fxt.dao.entity.project.GenPolicy;
import com.fxlabs.fxt.dao.entity.project.Project;
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
public class AutoCodeGenerator extends BasicBaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "auto_code_config_id")
    private AutoCodeConfig autoCodeConfig;

    private String type;
    private String assertions;
    private TestSuiteSeverity severity;
    private String database;

    @Column(name = "generator_inactive")
    protected boolean inactive = false;

}

