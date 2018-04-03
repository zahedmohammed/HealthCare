package com.fxlabs.fxt.codegen.code;

import com.fxlabs.fxt.codegen.generators.Generator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StubHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private List<Generator> generators = new ArrayList<>();

    public void register(Generator generator) {
        this.generators.add(generator);
    }


    public List<TestSuiteMin> handle(String path, HttpMethod method, Operation op) {

        logger.info("{} {}", path, method);
        List<TestSuiteMin> suites = new ArrayList<>();

        this.generators.parallelStream().forEach(g -> {
            try {
                TestSuiteMin ts = g.generate(path, method, op);
                if (ts != null) {
                    suites.add(ts);
                }
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage(), e);
            }
        });

        return suites;
    }
}