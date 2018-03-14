package com.fxlabs.fxt.dao.entity.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

/**
 * @author Intesar Shannan Mohammed
 */

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RunTask {


    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private Date startTime;
    private Date endTime;

    private Long totalTests;

    private Long totalSuiteCompleted = 0L;
    private Long totalTestCompleted = 0L;

    private Long failedTests = 0L;
    private Long skippedTests = 0L;
    private Long totalTime = 0L;
    private Long totalBytes = 0L;

}

