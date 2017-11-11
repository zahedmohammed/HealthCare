package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
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
public class ProjectRun extends BaseDto<String> {

    private Project project;

    private ProjectJob projectJob;

    private Long runId;
    private String name;
    private String description;

    private String status;
    private Date startTime;
    private Date endTime;


    private ProjectEnvironment projectEnvironment;

    private List<String> dataSetTags;

    private String region;


}

