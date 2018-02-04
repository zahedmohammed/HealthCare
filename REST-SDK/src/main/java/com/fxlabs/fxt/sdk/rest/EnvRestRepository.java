package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Environment;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class EnvRestRepository extends GenericRestRespository<Environment> {


    @Autowired
    public EnvRestRepository() {
        super(paramTypeRefMap.get(Environment.class), paramTypeRefMap.get(Environment[].class));

    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/envs";
    }


    public Response<List<Environment>> findByProjectId(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<List<Environment>>> response = restTemplate.exchange(getUrl() + "/project-id/" + id, HttpMethod.GET, request, paramTypeRefMap.get(Environment[].class));

        //logger.info(response.getBody());
        return response.getBody();

    }


}
