package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectFile;
import com.fxlabs.fxt.dto.project.ProjectImports;
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
public class ProjectRestRepository extends GenericRestRespository<Project> {


    @Autowired
    public ProjectRestRepository() {
        super(paramTypeRefMap.get(Project.class), paramTypeRefMap.get(Project[].class));

    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/projects";
    }

    public Project findByName(String id) {

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<Project>> response = restTemplate.exchange(getUrl() + "/name/" + id, HttpMethod.GET, request, paramTypeRefMap.get(Project.class));

        //logger.info(response.getBody());
        return response.getBody().getData();

    }

    public Response<Project> findByOrgAndName(String name) {

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<Project>> response = restTemplate.exchange(getUrl() + "/find-by-name/" + name, HttpMethod.GET, request, paramTypeRefMap.get(Project.class));
        return response.getBody();

    }

    public Response<Project> findById(String id) {

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<Project>> response = restTemplate.exchange(getUrl() + "/" + id, HttpMethod.GET, request, paramTypeRefMap.get(Project.class));

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<List<ProjectFile>> findProjectChecksums(String projectId) {

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<List<ProjectFile>>> response = restTemplate.exchange(getUrl() + "/" + projectId + "/project-checksums", HttpMethod.GET, request, paramTypeRefMap.get(ProjectFile[].class));

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<List<ProjectFile>> findProjectChecksumsAll(String projectId) {

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<List<ProjectFile>>> response = restTemplate.exchange(getUrl() + "/" + projectId + "/project-checksums-all", HttpMethod.GET, request, paramTypeRefMap.get(ProjectFile[].class));

        //logger.info(response.getBody());
        return response.getBody();

    }

    public Response<Boolean> saveImports(ProjectImports projectImports, String projectId) {

        HttpEntity<ProjectImports> request = new HttpEntity<>(projectImports, getHeaders());

        ResponseEntity<Response<Boolean>> response = restTemplate.exchange(getUrl() + "/" + projectId + "/project-imports", HttpMethod.POST, request, paramTypeRefMap.get(Boolean.class));

        return response.getBody();

    }


}
