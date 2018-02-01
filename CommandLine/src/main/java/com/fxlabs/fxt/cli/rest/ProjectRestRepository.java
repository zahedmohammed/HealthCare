package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ProjectRestRepository extends GenericRestRespository<Project> {


    @Autowired
    public ProjectRestRepository(@Value("${fx.master.url}") String url,
                                 @Value("${fx.master.accessKey}") String username,
                                 @Value("${fx.master.secretKey}") String password) {
        super(url + "/api/v1/projects", username, password, paramTypeRefMap.get(Project.class), paramTypeRefMap.get(Project[].class));

    }

    public Project findByName(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<Response<Project>> response = restTemplate.exchange(url + "/name/" + id, HttpMethod.GET, request, paramTypeRefMap.get(Project.class));

        //logger.info(response.getBody());
        return response.getBody().getData();

    }


}
