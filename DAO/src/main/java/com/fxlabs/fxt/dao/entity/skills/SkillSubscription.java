package com.fxlabs.fxt.dao.entity.skills;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SkillSubscription extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private SkillType skillType;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @ElementCollection
    @CollectionTable(
            name = "skill_subscription_opts",
            joinColumns = @JoinColumn(name = "skill_subscription_id")
    )
    private List<Opt> opts;

}

