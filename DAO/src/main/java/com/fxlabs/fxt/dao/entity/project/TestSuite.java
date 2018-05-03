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

    @Lob
    @Type(type="org.hibernate.type.TextType")
    private String description;

    @Enumerated(EnumType.STRING)
    private TestSuiteType type;

    private String endpoint;

    @Enumerated(EnumType.STRING)
    private HttpMethod method;

    private String auth;

    @ElementCollection
    private List<String> headers;

    @ElementCollection
    @CollectionTable(
            name = "test_cases",
            joinColumns = @JoinColumn(name = "test_suite_id")
    )
    private List<TestCase> testCases;

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

    private Boolean publishToMarketplace = false;

    @Enumerated(EnumType.STRING)
    private TestSuiteCategory category = TestSuiteCategory.BUG ;

    @Enumerated(EnumType.STRING)
    private TestSuiteSeverity severity = TestSuiteSeverity.MINOR;


    @PrePersist
    @PreUpdate
    public void setDefaults() {
        if (type == null) {
            this.type = TestSuiteType.SUITE;
        }
    }

}

