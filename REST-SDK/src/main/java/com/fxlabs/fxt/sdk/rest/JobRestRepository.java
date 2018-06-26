package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
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
public class JobRestRepository extends GenericRestRespository<Job> {


    @Autowired
    public JobRestRepository() {
        super(paramTypeRefMap.get(Job.class), paramTypeRefMap.get(Job[].class));

    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/jobs";
    }

    public Response<List<Job>> findByProjectId(String id) {

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<List<Job>>> response = restTemplate.exchange(getUrl() + "/project-id/" + id, HttpMethod.GET, request, paramTypeRefMap.get(Job[].class));

        //logger.info(response.getBody());
        return response.getBody();

    }


}
