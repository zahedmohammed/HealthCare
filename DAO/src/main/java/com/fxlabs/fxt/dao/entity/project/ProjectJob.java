package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectJob extends BaseEntity<String> {

    @ManyToOne
    private Project projectId;
    private String name;
    private String description;

    private ProjectEnvironment projectEnvironment;

    @ElementCollection
    private List<String> apiEndpointTags;
    @ElementCollection
    private List<String> dataSetTags;

    private String region;


}

