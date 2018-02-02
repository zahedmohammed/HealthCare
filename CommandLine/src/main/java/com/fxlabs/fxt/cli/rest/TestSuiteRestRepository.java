package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.project.TestSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class TestSuiteRestRepository extends GenericRestRespository<TestSuite> {


    @Autowired
    public TestSuiteRestRepository(@Value("${fx.master.url}") String url,
                                   @Value("${fx.master.accessKey}") String username,
                                   @Value("${fx.master.secretKey}") String password) {
        super(url + "/api/v1/test-suites", username, password, paramTypeRefMap.get(TestSuite.class), paramTypeRefMap.get(TestSuite[].class));

    }

}
