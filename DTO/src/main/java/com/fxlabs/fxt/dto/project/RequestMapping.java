package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RequestMapping {

    private String endPoint;
    private String method;
    private String sampleBody;
    private List<String> sqlQueryParams =  new ArrayList<>();
    private List<String> sqlProperties = new ArrayList<>();


}

