package com.fxlabs.fxt.dao.entity.event;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mohammed Shoukath ali
 */
@javax.persistence.Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Event extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Type eventType;

    @Enumerated(EnumType.STRING)
    private Entity entityType;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String entityId;
    private String name;

    private String link;

    private String userId;

    private String taskId;

    private String logId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;


}
