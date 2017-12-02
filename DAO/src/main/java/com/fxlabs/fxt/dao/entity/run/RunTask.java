package com.fxlabs.fxt.dao.entity.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Date;

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
    private Long totalSuiteCompleted = 0L;
    private Long totalTestCompleted = 0L;
    private Long failedTests = 0L;
    private Long skippedTests = 0L;
    private Long totalTime = 0L;

}

