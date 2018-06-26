package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectFile;
import com.fxlabs.fxt.dto.project.ProjectImports;
import com.fxlabs.fxt.dto.users.Users;
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
public class UsersRestRepository extends GenericRestRespository<Project> {


    @Autowired
    public UsersRestRepository() {
        super(paramTypeRefMap.get(Project.class), paramTypeRefMap.get(Project[].class));

    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/users";
    }

    public Response<Users> findByLogin() {

        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<Users>> response = restTemplate.exchange(getUrl() + "/status", HttpMethod.GET, request, paramTypeRefMap.get(Users.class));

        //logger.info(response.getBody());
        return response.getBody();

    }


}
