package com.fxlabs.fxt.codegen;

import com.fxlabs.fxt.codegen.code.StubGenerator;
import com.fxlabs.fxt.codegen.code.StubHandler;
import com.fxlabs.fxt.codegen.generators.access.AnonymousInvalidGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootConfiguration
//@ContextConfiguration(classes = {StubGenerator.class, StubHandler.class, AnonymousInvalidGenerator.class})
public class StubGeneratorTest {

    //@Autowired
    //private StubGenerator service;

    //@Test
    public void generate() {
        System.out.println("Testing...");
        StubGenerator service = new StubGenerator();

        service.generate("http://petstore.swagger.io/v2/swagger.json", "/tmp", null, null);
    }
}
