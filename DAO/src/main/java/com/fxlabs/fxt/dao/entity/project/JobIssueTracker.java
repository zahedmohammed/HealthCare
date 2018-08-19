package com.fxlabs.fxt.dao.entity.project;


import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.clusters.AccountType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String projectKey;
}
