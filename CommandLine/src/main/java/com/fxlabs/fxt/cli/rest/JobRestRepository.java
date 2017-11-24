package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.dto.run.Run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class JobRestRepository extends GenericRestRespository<Job> {


    @Autowired
    public JobRestRepository(@Value("${fx.master.url}") String url,
                             @Value("${fx.master.accessKey}") String username,
                             @Value("${fx.master.secretKey}") String password) {
        super(url + "/api/v1/jobs", username, password, paramTypeRefMap.get(Job.class), paramTypeRefMap.get(Job[].class));

    }

    public Run run(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<Response<Run>> response = restTemplate.exchange(url + "/" + id + "/run", HttpMethod.POST, request, paramTypeRefMap.get(Run.class));

        //logger.info(response.getBody());
        return response.getBody().getData();

    }

    public Run findInstance(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<Response<Run>> response = restTemplate.exchange(url + "/instance/" + id, HttpMethod.GET, request, paramTypeRefMap.get(Run.class));

        //logger.info(response.getBody());
        return response.getBody().getData();

    }


}
