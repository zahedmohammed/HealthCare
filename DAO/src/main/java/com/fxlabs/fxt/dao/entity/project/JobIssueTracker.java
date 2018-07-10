package com.fxlabs.fxt.dao.entity.project;


import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

/**
 * @author Mohammed Shoukath Ali
 *
 */


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobIssueTracker extends BaseEntity {

    private String name;
    private String url;
    private String account = "Default_GitHub";
}
