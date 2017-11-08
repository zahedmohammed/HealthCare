package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectAPIEndpoint extends BaseEntity<String> {

    @ManyToOne
    private Project projectId;
    private String name;
    private String description;
    private String endpoint;
    private String method; // http method
    private Long timeoutSeconds;
    //private List<>  // Assertions name, type, validation

}

