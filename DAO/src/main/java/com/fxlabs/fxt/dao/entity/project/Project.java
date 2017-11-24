package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.List;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Project extends BaseEntity<String> {


    private String name;
    private String description;

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

