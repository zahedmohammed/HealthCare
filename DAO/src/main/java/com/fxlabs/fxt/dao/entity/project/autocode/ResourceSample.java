package com.fxlabs.fxt.dao.entity.project.autocode;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.project.GenPolicy;
import com.fxlabs.fxt.dao.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mohammed Luqman Shareef
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourceSample extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_id")
    private Project project;

    private GenPolicy resource;

    private String sample;

}

