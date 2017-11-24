package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.project.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EnvRestRepository extends GenericRestRespository<Environment> {


    @Autowired
    public EnvRestRepository(@Value("${fx.master.url}") String url,
                             @Value("${fx.master.accessKey}") String username,
                             @Value("${fx.master.secretKey}") String password) {

        super(url + "/api/v1/environments", username, password, paramTypeRefMap.get(Environment.class), paramTypeRefMap.get(Environment[].class));
    }


}
