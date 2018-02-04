package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class TestSuiteRestRepository extends GenericRestRespository<TestSuite> {


    @Autowired
    public TestSuiteRestRepository() {
        super(paramTypeRefMap.get(TestSuite.class), paramTypeRefMap.get(TestSuite[].class));
    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/test-suites";
    }

}
