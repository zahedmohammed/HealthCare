package com.fxlabs.fxt.dao.entity.run;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.base.BaseEntityES;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.Date;

//@SolrDocument(collection = "fx")
@Document(indexName = "elasticsearch")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = false)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class TestSuiteResponse extends BaseEntityES {

    //@ManyToOne(cascade = CascadeType.REFRESH)
    //private TestSuite projectDataSet;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String testSuite;
    private String runId;
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


    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "modified_by")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "modified_date")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @Version
    private Long version;

    private boolean deleted = false;


}

