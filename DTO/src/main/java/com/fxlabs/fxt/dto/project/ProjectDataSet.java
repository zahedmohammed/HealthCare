package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectDataSet extends BaseDto<String> {

    private Project project;
    private String endpoint;
    private String method;
    private String request;
    private String result;
    private String assertions;
    private List<String> tags;

}

