package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.base.TestSuitesDeletedDto;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    public void deleteTestSuitesByProject(TestSuitesDeletedDto testSuitesDeletedDto) {

        try {
            HttpEntity<TestSuitesDeletedDto> request = new HttpEntity<>(testSuitesDeletedDto, this.getHeaders());
            String url = getUrl();

            ResponseEntity<Response<String>> response = restTemplate.exchange(url +  "/deletedtestsuites", HttpMethod.PUT, request, referenceList);
        }catch(Exception ex){
            logger.warn(ex.getLocalizedMessage());
        }
//        return response.getBody();

    }

}
