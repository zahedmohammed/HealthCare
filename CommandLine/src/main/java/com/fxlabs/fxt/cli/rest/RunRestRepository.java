package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.ProjectEnvironment;
import com.fxlabs.fxt.dto.run.DataSet;
import com.fxlabs.fxt.dto.run.Run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
public class RunRestRepository extends GenericRestRespository<Run> {


    @Autowired
    public RunRestRepository(@Value("${fx.master.url}") String url,
                             @Value("${fx.master.accessKey}") String username,
                             @Value("${fx.master.secretKey}") String password) {

        super(url + "/api/v1/runs", username, password, paramTypeRefMap.get(Run.class), paramTypeRefMap.get(Run[].class));
    }

    public Response<List<DataSet>> findTestSuitesByRunId(String id, Integer page, Integer pageSize) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(httpHeaders);

        ResponseEntity<Response<List<DataSet>>> response = restTemplate.exchange(url + "/" + id + "/test-suites", HttpMethod.GET, request, paramTypeRefMap.get(DataSet[].class));

        //logger.info(response.getBody());
        return response.getBody();

    }


}
