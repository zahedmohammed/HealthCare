package com.fxlabs.fxt.dto.it;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Mohammed Shoukath Ali
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IssueTrackerSaving implements Serializable{
    private String itid;
    private String projectId;
    private String jobId;
    private String testSuiteName;
    private String testCaseNumber;

    private Long totalHourSaving;
    private Long totalCostSaving;
    private int hourlyBugRate;
    private int hourlyBugValidation;
    private int validatedCount;
}

