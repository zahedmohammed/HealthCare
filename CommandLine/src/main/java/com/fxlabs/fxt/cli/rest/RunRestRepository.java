package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.project.ProjectEnvironment;
import com.fxlabs.fxt.dto.run.Run;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class RunRestRepository extends GenericRestRespository<Run> {


    @Autowired
    public RunRestRepository(@Value("${fx.master.url}") String url,
                             @Value("${fx.master.accessKey}") String username,
                             @Value("${fx.master.secretKey}") String password) {

        super(url + "/api/v1/runs", username, password, paramTypeRefMap.get(Run.class), paramTypeRefMap.get(Run[].class));
    }


}
