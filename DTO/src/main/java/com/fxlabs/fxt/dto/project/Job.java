package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Job extends BaseDto<String> {

    private ProjectMinimalDto project;

    private String name;
    private String refId;
    private String description;

    private String environment;

    private List<String> tags;

    private String regions;


}

