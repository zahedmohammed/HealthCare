package com.fxlabs.fxt.cli.rest;

import com.fxlabs.fxt.dto.project.TestSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DataSetRestRepository extends GenericRestRespository<TestSuite> {


    @Autowired
    public DataSetRestRepository(@Value("${fx.master.url}") String url,
                                 @Value("${fx.master.accessKey}") String username,
                                 @Value("${fx.master.secretKey}") String password) {
        super(url + "/api/v1/datasets", username, password, paramTypeRefMap.get(TestSuite.class), paramTypeRefMap.get(TestSuite[].class));

    }


}
