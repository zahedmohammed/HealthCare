package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TestSuite extends BaseEntity<String> {

    @ManyToOne(cascade = CascadeType.REFRESH)
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
    private List<String> request;

    @ElementCollection
    private List<String> assertions;

    @ElementCollection
    private List<String> tags;

    @ElementCollection
    private List<String> authors;

    @ElementCollection
    private List<String> after;

    @PrePersist
    @PreUpdate
    public void setDefaults() {
        if (type == null) {
            this.type = TestSuiteType.SUITE;
        }
    }

}

