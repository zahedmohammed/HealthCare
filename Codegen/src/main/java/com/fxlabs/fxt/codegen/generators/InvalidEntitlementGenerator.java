package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.codegen.generators.base.Generator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import io.swagger.models.Operation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "invalidEntitlementGenerator")
public class InvalidEntitlementGenerator implements Generator {

    @Override
    public List<TestSuiteMin> generate(String path, io.swagger.models.HttpMethod method, Operation op) {
        return null;
    }
}
