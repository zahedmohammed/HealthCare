package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

    private String endpoint;
    private String method;
    private String auth;
    @ElementCollection
    private List<String> headers;
    @ElementCollection
    private List<String> request;
    @ElementCollection
    private List<String> assertions;
    @ElementCollection
    private List<String> tags;

}

