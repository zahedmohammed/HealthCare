package com.fxlabs.fxt.dao.entity.it;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.base.Visibility;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.skills.Opt;
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
public class IssueTracker extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    private String name;
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "skill_subscription_opts",
            joinColumns = @JoinColumn(name = "skill_subscription_id")
    )
    private List<Opt> opts;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Account account;

    private String prop1; // url
    private String prop2; // access-key
    private String prop3; // secret-key
    private String prop4; // project-name/key/id
    private String prop5; //

    @Column(name = "_state")
    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

}

