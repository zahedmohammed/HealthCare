package com.fxlabs.fxt.dto.run;


import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectEnvironment;
import com.fxlabs.fxt.dto.project.ProjectJob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Run extends BaseDto<String> {

    private ProjectJob projectJob;

    private Long runId;

    private String status;

    List<RunTask> tasks;


}

