package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DataSetRestRepository extends GenericRestRespository<ProjectDataSet> {


    @Autowired
    public DataSetRestRepository(@Value("${fx.master.url}") String url,
                                 @Value("${fx.master.accessKey}") String username,
                                 @Value("${fx.master.secretKey}") String password) {
        super(url + "/api/v1/datasets", username, password, paramTypeRefMap.get(ProjectDataSet.class), paramTypeRefMap.get(ProjectDataSet[].class));

    }


}
