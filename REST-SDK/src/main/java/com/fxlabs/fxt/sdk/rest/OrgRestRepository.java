package com.fxlabs.fxt.sdk.rest;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.users.OrgUsers;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.sdk.services.CredUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class OrgRestRepository extends GenericRestRespository<Project> {


    @Autowired
    public OrgRestRepository() {
        super(paramTypeRefMap.get(Project.class), paramTypeRefMap.get(Project[].class));

    }

    protected String getUrl() {
        return CredUtils.url.get() + "/api/v1/orgs";
    }

    public Response<OrgUsers> loginStatus() {
        HttpEntity<Void> request = new HttpEntity<>(getHeaders());

        ResponseEntity<Response<OrgUsers>> response = restTemplate.exchange(getUrl() + "/login-status", HttpMethod.GET, request, paramTypeRefMap.get(OrgUsers.class));

        //logger.info(response.getBody());
        return response.getBody();
    }


}
