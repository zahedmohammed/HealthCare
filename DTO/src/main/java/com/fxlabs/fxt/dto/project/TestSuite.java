package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.IdDto;
import com.fxlabs.fxt.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestSuite extends BaseDto<String> {

    private NameDto project;
    private String name;
    private TestSuiteType type;
    private String endpoint;
    private HttpMethod method;
    private String auth;
    private List<String> headers;
    private List<String> request;
    private List<String> assertions;
    private List<String> tags;
    private List<String> developers;
    private List<String> after;

}

