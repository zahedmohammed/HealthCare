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
public class ProjectJob extends BaseEntity<String> {

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Project project;
    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private ProjectEnvironment projectEnvironment;

    @ElementCollection
    private List<String> dataSetTags;

    private String region;


}

