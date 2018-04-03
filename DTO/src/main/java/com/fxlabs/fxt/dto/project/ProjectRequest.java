package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectRequest extends BaseDto<String> {

    private String projectId;
    private NameDto org;

    private GenPolicy genPolicy;
    private String openAPISpec;

    private String url;
    private String branch;
    private String username;
    private String password;
    private String lastCommit;

    // data-from-ui
    private String name;
    private String description;
    private ProjectType projectType;
    private ProjectVisibility visibility;


}

