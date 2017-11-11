package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectRun extends BaseEntity<String> {

    @ManyToOne
    private Project project;

    @ManyToOne
    private ProjectJob projectJob;

    private Long runId;
    private String name;
    private String description;

    private String status;
    private Date startTime;
    private Date endTime;


    private ProjectEnvironment projectEnvironment;

    @ElementCollection
    private List<String> dataSetTags;

    private String region;


}

