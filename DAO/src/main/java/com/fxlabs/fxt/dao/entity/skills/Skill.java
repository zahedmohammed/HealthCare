package com.fxlabs.fxt.dao.entity.skills;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.project.ProjectType;
import com.fxlabs.fxt.dao.entity.project.ProjectVisibility;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Skill extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private SkillType skillType;

    private String key;

    @ElementCollection
    @CollectionTable(
            name = "skill_opts",
            joinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Opt> opts;

}

