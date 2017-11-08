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

    private ProjectAPIEndpoint projectEndpointId;
    private String request;
    private String result; // http method
    private List<String> tags;
    //private List<>  // name, type, validation

}

