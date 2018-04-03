package com.fxlabs.fxt.codegen.generators;

import com.fxlabs.fxt.dto.project.HttpMethod;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import io.swagger.models.Operation;
import org.springframework.stereotype.Component;

@Component(value = "validGenerator")
public class ValidGenerator implements Generator {

    @Override
    public TestSuiteMin generate(String path, io.swagger.models.HttpMethod method, Operation op) {
        return null;
    }
}
