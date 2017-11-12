package com.fxlabs.fxt.dao.entity.run;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.ProjectEnvironment;
import com.fxlabs.fxt.dao.entity.project.ProjectJob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

//@SolrDocument(collection = "fx")
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RunTask {


    private String name;
    private String description;

    private String status; // WAITING --> PROCESSING --> COMPLETED
    private Date startTime;
    private Date endTime;

    private Long totalTests;
    private Long totalTestCompleted;
    private Long failedTests;
    private Long totalTime;

}

