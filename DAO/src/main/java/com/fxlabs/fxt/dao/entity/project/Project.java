package com.fxlabs.fxt.dao.entity.project;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Project extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Account account;

    @Enumerated(EnumType.STRING)
    private GenPolicy genPolicy;

    @Column(name = "open_api_spec_uri")
    private String openAPISpec;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSync;

    @ElementCollection
    private List<String> licenses;

    private String url;
    private String branch;
    private String lastCommit;

    private Integer autoGenSuites = 0;
//
////    @ElementCollection
//    private List<Endpoint> apiEndpoints;


}

