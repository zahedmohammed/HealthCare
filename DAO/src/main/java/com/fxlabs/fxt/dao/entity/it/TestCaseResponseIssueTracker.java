package com.fxlabs.fxt.dao.entity.it;


import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;

/**
 * @author Mohammed Shoukath Ali
 */
@Document(indexName = "fx-testcase-responses-issuetracker")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestCaseResponseIssueTracker extends BaseEntity {

    // ProjectId//JobId//SuiteId//TestCase
    private String testCaseResponseIssueTrackerId;
   // Status  issue_id  validations
    private String issueId;
    private Integer validations;
    private String status;


}
