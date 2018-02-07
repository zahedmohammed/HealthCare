package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class RunRestRepository extends GenericRestRespository<Run> {

    @Autowired
    public RunRestRepository() {

        super(paramTypeRefMap.get(Run.class), paramTypeRefMap.get(Run[].class));
    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/runs";
    }

    public Run run(String id, String region, String tags, String env, String suites) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<Run>> response = restTemplate.exchange(getUrl() + "/job/" + id + "?region={region}&env={env}&tags={tags}&suites={suites}",
                HttpMethod.POST, request, paramTypeRefMap.get(Run.class), region, env, tags, suites);

        //logger.info(response.getBody());
        return response.getBody().getData();

    }

    public Run findInstance(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<Run>> response = restTemplate.exchange(getUrl() + "/" + id, HttpMethod.GET, request, paramTypeRefMap.get(Run.class));

        //logger.info(response.getBody());
        return response.getBody().getData();

    }

    public Response<List<TestSuiteResponse>> findTestSuitesByRunId(String id, Integer page, Integer pageSize) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<List<TestSuiteResponse>>> response = restTemplate.exchange(getUrl() + "/" + id + "/test-suite-responses", HttpMethod.GET, request, paramTypeRefMap.get(TestSuiteResponse[].class));

        //logger.info(response.getBody());
        return response.getBody();

    }


}
