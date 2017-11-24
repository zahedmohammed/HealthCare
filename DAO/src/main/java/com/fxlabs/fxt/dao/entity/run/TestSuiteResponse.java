package com.fxlabs.fxt.dao.entity.run;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestSuiteResponse extends BaseEntity<String> {

    //@ManyToOne(cascade = CascadeType.REFRESH)
    //private TestSuite projectDataSet;
    private String testSuite;
    private String runId;
    private Integer tests;
    private String response;
    private String logs;
    private Date requestStartTime;
    private Date requestEndTime;
    private Long requestTime;
    private Long totalPassed = 0L;
    private Long totalFailed = 0L;
    private Long totalSkipped = 0L;
    private String status;


}

