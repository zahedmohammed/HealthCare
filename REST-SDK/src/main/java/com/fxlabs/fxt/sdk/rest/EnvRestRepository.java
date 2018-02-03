package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Environment;
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
    public EnvRestRepository(@Value("${fx.master.url}") String url,
                             @Value("${fx.master.accessKey}") String username,
                             @Value("${fx.master.secretKey}") String password) {
        super(url + "/api/v1/envs", username, password, paramTypeRefMap.get(Environment.class), paramTypeRefMap.get(Environment[].class));

    }

    public Response<List<Environment>> findByProjectId(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<Response<List<Environment>>> response = restTemplate.exchange(url + "/project-id/" + id, HttpMethod.GET, request, paramTypeRefMap.get(Environment[].class));

        //logger.info(response.getBody());
        return response.getBody();

    }


}
