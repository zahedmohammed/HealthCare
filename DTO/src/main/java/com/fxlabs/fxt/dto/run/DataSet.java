package com.fxlabs.fxt.dto.run;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.project.ProjectDataSet;
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
public class DataSet extends BaseDto<String> {

    private ProjectDataSet projectDataSet;
    private String response;
    private String logs;
    private Date requestStartTime;
    private Date requestEndTime;
    private Boolean success;

}

