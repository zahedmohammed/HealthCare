package com.fxlabs.fxt.codegen.code;

import com.fxlabs.fxt.codegen.generators.base.Generator;
import com.fxlabs.fxt.dto.project.TestSuiteMin;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class StubHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private List<Generator> generators = new ArrayList<>();

    public void register(Generator generator) {
        this.generators.add(generator);
    }


    public List<TestSuiteMin> handle(String path, HttpMethod method, Operation op, AutoCodeConfig config) {

        logger.debug("{} {}", path, method);
        List<TestSuiteMin> suites = Collections.synchronizedList(new ArrayList<>());

        this.generators.parallelStream().forEach(g -> {
            try {
                g.setAutoCodeConfig(config);
                List<TestSuiteMin> list = g.generate(path, method, op);
                if (list != null && !list.isEmpty()) {
                    suites.addAll(list);
                }
            } catch (Exception e) {
                logger.warn(e.getLocalizedMessage(), e);
            }
        });

        return suites;
    }
}
