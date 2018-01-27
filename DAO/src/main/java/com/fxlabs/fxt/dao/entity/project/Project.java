package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Project extends BaseEntity<String> {

    @ManyToOne
    @JoinColumn(name="org_id")
    private Org org;

    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSync;

    @ElementCollection
    private List<String> licenses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Environment> environments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;

    @PrePersist
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
    }

}

