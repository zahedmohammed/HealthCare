package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 */
@Document(indexName = "fx-auto-suggest")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AutoSuggestion extends BaseEntity {

//    @ManyToOne
//    @JoinColumn(name = "project_id")
    private String projectId;

    private String suggestionId;

    private String testSuiteId;
    private String testSuiteName;
    private String testCaseNumber;
    private String region;
    private String respStatusCode;
    private String endPoint;
    private String method;
    private String category;
    private String severity;
    private String issueDesc;
    private String suggestion;
    private String estimates;

    @Enumerated(EnumType.STRING)
    private SuggestionStatus status;

}

