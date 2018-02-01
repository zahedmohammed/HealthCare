package com.fxlabs.fxt.dao.entity.run;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Date;

@Document(indexName = "fxindex")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestSuiteResponse extends BaseEntity {

    private String testSuite;
    private String runId;
    private String region;
    private Integer tests;
    private String response;
    @Lob
    //@Type(type = "org.hibernate.type.StringClobType")
    private String logs;
    private Date requestStartTime;
    private Date requestEndTime;
    private Long requestTime;
    private Long totalPassed = 0L;
    private Long totalFailed = 0L;
    private Long totalSkipped = 0L;
    private String status;

}

