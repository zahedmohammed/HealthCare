package com.fxlabs.fxt.dao.entity.skills;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.it.IssueTracker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Intesar Shannan Mohammed
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SubscriptionTask extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "subscription_id")
    private IssueTracker subscription;

    @Column(name = "_type")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Column(name = "_result")
    @Enumerated(EnumType.STRING)
    private TaskResult result;

    @Column(name = "_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String logs;

    private String clusterId;

}

