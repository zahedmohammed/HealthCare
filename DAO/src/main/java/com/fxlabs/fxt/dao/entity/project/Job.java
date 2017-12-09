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
public class Job extends BaseEntity<String> {

    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Project project;

    //@ManyToOne(cascade = CascadeType.REFRESH)
    private String environment;

    @ElementCollection
    private List<String> tags;

    private String region;


}

