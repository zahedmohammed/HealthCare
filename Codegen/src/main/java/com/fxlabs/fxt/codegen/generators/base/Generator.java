package com.fxlabs.fxt.codegen.generators.base;

import com.fxlabs.fxt.dto.project.TestSuiteMin;
import io.swagger.models.Operation;

import java.util.List;

public interface Generator {

    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op);

}
