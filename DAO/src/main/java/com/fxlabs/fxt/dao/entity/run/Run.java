package com.fxlabs.fxt.dao.entity.run;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.project.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Run extends BaseEntity<String> {

    @ManyToOne
    private Job job;

    private Long runId;

    @Embedded
    private RunTask task;

    // override region, tags, env in reference Job entity.
    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name = "run_attributes", joinColumns = @JoinColumn(name = "run_id"))
    private Map<String, String> attributes = new HashMap<>(); // maps from attribute name to value


}

