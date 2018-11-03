package com.fxlabs.issues.dao.entity.project;

import com.fxlabs.issues.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fx-issues")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Issue extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_id")
    private Project project;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    private String endpoint;

    @Enumerated(EnumType.STRING)
    private HttpMethod method;

    @ElementCollection
    private List<String> headers;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String requestBody;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String responseBody;

    private String responseHeaders;

    private String statusCode;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String assertions;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String failedAssertions;

    private String result;

    @ElementCollection
    private List<String> tags;

    private String env;

    private String issueName;

    private String issueType;

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    private String assignedTo;

}

