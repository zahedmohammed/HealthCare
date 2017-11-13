package com.fxlabs.fxt.cli;

import com.fxlabs.fxt.dto.base.IdDto;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.Run;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.*;

@Component
public class RestRespository {

    final Logger logger = LoggerFactory.getLogger(getClass());


    public final static HashMap<Class, ParameterizedTypeReference> paramTypeRefMap = new HashMap<>();

    static {
        paramTypeRefMap.put(Project.class, new ParameterizedTypeReference<Response<Project>>() {
        });
        paramTypeRefMap.put(ProjectEnvironment.class, new ParameterizedTypeReference<Response<ProjectEnvironment>>() {
        });
        paramTypeRefMap.put(ProjectDataSet.class, new ParameterizedTypeReference<Response<ProjectDataSet>>() {
        });
        paramTypeRefMap.put(ProjectJob.class, new ParameterizedTypeReference<Response<ProjectJob>>() {
        });
        paramTypeRefMap.put(Run.class, new ParameterizedTypeReference<Response<Run>>() {
        });
    }

    public Project saveProject(String name, String url, String u, String p) {

        // execute request
        RestTemplate restTemplate = new RestTemplate();

        HttpMethod method = HttpMethod.POST;

        HttpHeaders httpHeaders = getHttpHeaders(u, p);

        Project project = new Project();
        project.setName(name);

        HttpEntity<Project> request = new HttpEntity<>(project, httpHeaders);

        ResponseEntity<Response<Project>> response = restTemplate.exchange(url + "/api/v1/projects", method, request, paramTypeRefMap.get(Project.class));

        logger.info(response.getBody().getData().toString());
        return response.getBody().getData();

    }

    public ProjectEnvironment saveEnv(Project project, String name, String baseUrl, String method_, String envU, String envP, String url, String u, String p) {

        // execute request
        RestTemplate restTemplate = new RestTemplate();

        HttpMethod method = HttpMethod.POST;

        HttpHeaders httpHeaders = getHttpHeaders(u, p);

        ProjectEnvironment env = new ProjectEnvironment();
        env.setName(name);
        env.setBaseUrl(baseUrl);
        NameDto proj = new NameDto();
        proj.setId(project.getId());
        proj.setVersion(project.getVersion());
        env.setProject(proj);
        ProjectCredential cred = new ProjectCredential();
        cred.setName("Default");
        cred.setMethod(method_);
        cred.setUsername(envU);
        cred.setPassword(envP);
        List<ProjectCredential> list = new ArrayList<>();
        list.add(cred);
        env.setCredentials(list);

        HttpEntity<ProjectEnvironment> request = new HttpEntity<>(env, httpHeaders);

        ResponseEntity<Response<ProjectEnvironment>> response = restTemplate.exchange(url + "/api/v1/environments", method, request, paramTypeRefMap.get(ProjectEnvironment.class));

        return response.getBody().getData();

    }

    public String saveDS(Project project, String name, String endpoint, String method_, String request_, String assertions, List<String> tags, String url, String u, String p) {

        // execute request
        RestTemplate restTemplate = new RestTemplate();

        HttpMethod method = HttpMethod.POST;

        HttpHeaders httpHeaders = getHttpHeaders(u, p);

        ProjectDataSet ds = new ProjectDataSet();
        ds.setName(name);
        ds.setEndpoint(endpoint);
        ds.setMethod(method_);
        ds.setRequest(request_);
        ds.setAssertions(assertions);
        ds.setTags(tags);

        NameDto proj = new NameDto();
        proj.setId(project.getId());
        proj.setVersion(project.getVersion());
        ds.setProject(proj);


        HttpEntity<ProjectDataSet> request = new HttpEntity<>(ds, httpHeaders);

        ResponseEntity<Response<ProjectDataSet>> response = restTemplate.exchange(url + "/api/v1/datasets", method, request, paramTypeRefMap.get(ProjectDataSet.class));

        return response.getBody().getData().getId();

    }

    public String saveJob(Project project, String name, ProjectEnvironment env, List<String> tags, String region, String url, String u, String p) {

        // execute request
        RestTemplate restTemplate = new RestTemplate();

        HttpMethod method = HttpMethod.POST;

        HttpHeaders httpHeaders = getHttpHeaders(u, p);

        ProjectJob job = new ProjectJob();
        job.setName(name);
        NameDto proj = new NameDto();
        proj.setId(project.getId());
        proj.setVersion(project.getVersion());
        job.setProject(proj);

        job.setDataSetTags(tags);

        job.setProjectEnvironment(env);

        job.setRegion(region);

        HttpEntity<ProjectJob> request = new HttpEntity<>(job, httpHeaders);

        ResponseEntity<Response<ProjectJob>> response = restTemplate.exchange(url + "/api/v1/jobs", method, request, paramTypeRefMap.get(ProjectJob.class));

        return response.getBody().getData().getId();

    }


    private HttpHeaders getHttpHeaders(String u, String p) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", createBasicAuth(u, p));
        return httpHeaders;
    }

    private String createBasicAuth(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;

    }


}
