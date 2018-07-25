package com.fxlabs.fxt.dao.entity.project.autocode;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.project.GenPolicy;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AutoCodeConfig extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private GenPolicy genPolicy;

    @Column(name = "open_api_spec_uri")
    private String openAPISpec;

}

