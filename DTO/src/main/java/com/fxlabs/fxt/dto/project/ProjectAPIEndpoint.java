package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectAPIEndpoint extends BaseDto<String> {

    private Project projectId;
    private String name;
    private String description;
    private String endpoint;
    private String method; // http method
    private Long timeoutSeconds;
    //private List<>  // Assertions name, type, validation

}

