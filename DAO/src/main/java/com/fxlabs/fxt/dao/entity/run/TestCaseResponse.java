package com.fxlabs.fxt.dao.entity.run;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;

/**
 * @author Intesar Shannan Mohammed
 */

@Document(indexName = "fxtestsuiteresponse")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class TestCaseResponse extends BaseEntity {

    private String project;
    private String job;
    private String env;
    private String region;
    private String suite;
    private String testCase;
    private String endpoint;
    private String endpointEval;
    private String request;
    private String requestEval;
    private String response;
    private String logs;
    private String itKey;

}

