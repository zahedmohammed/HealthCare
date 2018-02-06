package com.fxlabs.fxt.dao.entity.alerts;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fxalerts")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Alert extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private AlertType type;

    @Enumerated(EnumType.STRING)
    private AlertSeverity severity;

    @Enumerated(EnumType.STRING)
    private AlertState state;

    @Enumerated(EnumType.STRING)
    private AlertStatus status;

    @Enumerated(EnumType.STRING)
    private AlertRefType refType;

    private String refId;
    private String refName;
    private String subject;
    @Lob
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private Date readDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date healedDate;

    @ElementCollection
    private List<String> users = new ArrayList<>();


}
