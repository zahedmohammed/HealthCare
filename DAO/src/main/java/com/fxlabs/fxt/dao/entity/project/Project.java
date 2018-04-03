package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
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
public class Project extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @Enumerated(EnumType.STRING)
    private ProjectVisibility visibility;

    @Enumerated(EnumType.STRING)
    private GenPolicy genPolicy;

    @Column(name = "open_api_spec_uri")
    private String openAPISpec;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSync;

    @ElementCollection
    private List<String> licenses;

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Environment> environments;

    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Job> jobs;

    /*@PrePersist
    @PreUpdate
    public void updateEnvironments() {
        if (!CollectionUtils.isEmpty(this.environments)) {
            for (Environment env : environments) {
                env.setProject(this);
            }
        }

        if (!CollectionUtils.isEmpty(this.jobs)) {
            for (Job job : jobs) {
                job.setProject(this);
            }
        }
    }*/

}

