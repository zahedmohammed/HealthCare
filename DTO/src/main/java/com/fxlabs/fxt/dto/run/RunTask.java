package com.fxlabs.fxt.dto.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RunTask {


    private String name;
    private String description;

    private TaskStatus status;
    private Date startTime;
    private Date endTime;
    private Long timeTaken;

    private Long totalTests;
    private Long totalSuiteCompleted = 0L;
    private Long totalTestCompleted = 0L;
    private Long failedTests = 0L;
    private Long skippedTests = 0L;
    private Long totalTime = 0L;
    private Long totalBytes = 0L;

}
