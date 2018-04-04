package com.fxlabs.fxt.codegen.generators.data;

import com.fxlabs.fxt.codegen.generators.Generator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import io.swagger.models.Operation;
import org.springframework.stereotype.Component;

@Component(value = "invalidInjectionPostgresGenerator")
public class InvalidInjectionPostgresGenerator implements Generator {

    @Override
    public TestSuiteMin generate(String path, io.swagger.models.HttpMethod method, Operation op) {
        return null;
    }
}
