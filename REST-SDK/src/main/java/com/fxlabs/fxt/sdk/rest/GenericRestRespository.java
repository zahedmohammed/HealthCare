package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.TestSuiteResponse;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public class GenericRestRespository<T> {

    public final static HashMap<Class, ParameterizedTypeReference> paramTypeRefMap = new HashMap<>();

    static {
        paramTypeRefMap.put(Project.class, new ParameterizedTypeReference<Response<Project>>() {
        });
        paramTypeRefMap.put(Environment.class, new ParameterizedTypeReference<Response<Environment>>() {
        });
        paramTypeRefMap.put(TestSuite.class, new ParameterizedTypeReference<Response<TestSuite>>() {
        });
        paramTypeRefMap.put(Job.class, new ParameterizedTypeReference<Response<Job>>() {
        });
        paramTypeRefMap.put(Run.class, new ParameterizedTypeReference<Response<Run>>() {
        });

        paramTypeRefMap.put(Project[].class, new ParameterizedTypeReference<Response<List<Project>>>() {
        });
        paramTypeRefMap.put(Environment[].class, new ParameterizedTypeReference<Response<List<Environment>>>() {
        });
        paramTypeRefMap.put(TestSuite[].class, new ParameterizedTypeReference<Response<List<TestSuite>>>() {
        });
        paramTypeRefMap.put(Job[].class, new ParameterizedTypeReference<Response<List<Job>>>() {
        });
        paramTypeRefMap.put(Run[].class, new ParameterizedTypeReference<Response<List<Run>>>() {
        });
        paramTypeRefMap.put(TestSuiteResponse[].class, new ParameterizedTypeReference<Response<List<TestSuiteResponse>>>() {
        });
        paramTypeRefMap.put(ProjectFile[].class, new ParameterizedTypeReference<Response<List<ProjectFile>>>() {
        });
    }

    final Logger logger = LoggerFactory.getLogger(getClass());



    //protected HttpHeaders httpHeaders;
    protected ParameterizedTypeReference reference;
    protected ParameterizedTypeReference referenceList;


    public GenericRestRespository(ParameterizedTypeReference reference, ParameterizedTypeReference referenceList) {
        this.reference = reference;
        this.referenceList = referenceList;
    }

    public Response<T> save(T t) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<T> request = new HttpEntity<>(t, this.getHeaders());

        ResponseEntity<Response<T>> response = restTemplate.exchange(getUrl(), HttpMethod.POST, request, reference);

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<T> update(T t) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<T> request = new HttpEntity<>(t, this.getHeaders());

        ResponseEntity<Response<T>> response = restTemplate.exchange(getUrl(), HttpMethod.PUT, request, reference);

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<List<T>> saveAll(List<T> t) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<List<T>> request = new HttpEntity<>(t, this.getHeaders());

        ResponseEntity<Response<List<T>>> response = restTemplate.exchange(getUrl() + "/batch", HttpMethod.POST, request, referenceList);

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<List<T>> findAll() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(this.getHeaders());

        ResponseEntity<Response<List<T>>> response = restTemplate.exchange(getUrl(), HttpMethod.GET, request, referenceList);

        //logger.info(response.getBody());
        return response.getBody();

    }


    public Response<T> findById(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Void> request = new HttpEntity<>(this.getHeaders());

        ResponseEntity<Response<T>> response = restTemplate.exchange(getUrl() + "/instance/" + id, HttpMethod.GET, request, reference);

        //logger.info(response.getBody());
        return response.getBody();

    }

    protected HttpHeaders getHttpHeaders(String u, String p) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("Authorization", createBasicAuth(u, p));
        return httpHeaders;
    }

    protected String createBasicAuth(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }

    protected String getUrl() {
        return CredUtils.url.get();
    }

    protected String getUsername() {
        return CredUtils.username.get();
    }

    protected String getPassword() {
        return CredUtils.password.get();
    }

    protected HttpHeaders getHeaders() {
        return getHttpHeaders(getUsername(), getPassword());
    }


}