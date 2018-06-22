package com.fxlabs.fxt.dto.run;

import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestSuiteResponse extends BaseDto<String> {

    //private NameDto projectDataSet;
    private String testSuite;
    private String runId;
    private Long runNo;
    private String region;
    private Integer tests;
    private String response;
    private String logs;
    private Date requestStartTime;
    private Date requestEndTime;
    private Long requestTime;
    private Long totalPassed = 0L;
    private Long totalFailed = 0L;
    private Long totalSkipped = 0L;
    private Long totalBytes = 0L;
    private String status;

    private String category;
    private String severity;

}

