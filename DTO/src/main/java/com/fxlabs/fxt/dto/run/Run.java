package com.fxlabs.fxt.dto.run;


import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.project.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Run extends BaseDto<String> {

    private Job job;

    private Long runId;

    private RunTask task;

    // override region, tags, env in reference Job entity.
    private Map<String, String> attributes = new HashMap<>();

    private String regions;

    private Map<String, Long> stats = new HashMap<>();

    private Integer validations;

}

