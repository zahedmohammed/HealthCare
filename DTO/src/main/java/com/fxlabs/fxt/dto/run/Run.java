package com.fxlabs.fxt.dto.run;


import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.project.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Run extends BaseDto<String> {

    private Job job;

    private Long runId;

    private RunTask task;


}

