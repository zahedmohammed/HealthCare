package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.run.Run;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public class ProjectRestRepository extends GenericRestRespository<Project> {


    @Autowired
    public ProjectRestRepository(@Value("${fx.master.url}") String url,
                                 @Value("${fx.master.accessKey}") String username,
                                 @Value("${fx.master.secretKey}") String password) {
        super(url + "/api/v1/projects", username, password, paramTypeRefMap.get(Project.class), paramTypeRefMap.get(Project[].class));

    }


}
