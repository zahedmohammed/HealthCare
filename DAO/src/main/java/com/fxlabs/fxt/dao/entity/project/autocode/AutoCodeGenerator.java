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
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AutoCodeGenerator extends BasicBaseEntity {

    private String type;

    @ElementCollection
    private List<String> assertions;

    @Enumerated(EnumType.STRING)
    private TestSuiteSeverity severity;

    @Embedded
    private Database database;

    private boolean inactive = false;

    @ElementCollection
    @CollectionTable(
            name = "auto_code_generator_auto_code_generator_matches",
            joinColumns = @JoinColumn(name = "auto_code_generator_id")
    )
    private List<AutoCodeGeneratorMatches> autoCodeGeneratorMatches;

}

