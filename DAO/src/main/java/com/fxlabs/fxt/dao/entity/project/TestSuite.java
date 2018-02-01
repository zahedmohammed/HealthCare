package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fxtestsuite")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestSuite extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;

    @Enumerated(EnumType.STRING)
    private TestSuiteType type;

    private String endpoint;

    @Enumerated(EnumType.STRING)
    private HttpMethod method;

    private String auth;

    @ElementCollection
    private List<String> headers;

    @ElementCollection
    @Lob
    private List<String> requests;

    @ElementCollection
    private List<String> assertions;

    @ElementCollection
    private List<String> tags;

    @ElementCollection
    private List<String> authors;

    @ElementCollection
    private List<String> init;

    @ElementCollection
    private List<String> cleanup;

    @Embedded
    private Policies policies;

    @PrePersist
    @PreUpdate
    public void setDefaults() {
        if (type == null) {
            this.type = TestSuiteType.SUITE;
        }
    }

}

