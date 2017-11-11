package com.fxlabs.fxt.dto.run;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RunTask {


    private String name;
    private String description;

    private String status;
    private Date startTime;
    private Date endTime;

    private Long totalTests;
    private Long totalTestCompleted;
    private Long failedTests;

}
