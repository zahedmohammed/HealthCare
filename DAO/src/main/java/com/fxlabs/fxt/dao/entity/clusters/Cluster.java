package com.fxlabs.fxt.dao.entity.clusters;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import com.fxlabs.fxt.dao.entity.users.Org;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

/**
 * @author Intesar Shannan Mohammed
 */
@Document(indexName = "fxclusters")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cluster extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private CloudAccount cloudAccount;

    @Enumerated(EnumType.STRING)
    private ClusterDriver driver;

    @Enumerated(EnumType.STRING)
    private ClusterStatus status;

    @Enumerated(EnumType.STRING)
    private ClusterVisibility visibility;

    @Enumerated(EnumType.STRING)
    private ClusterCloud cloudType;

    private String name;
    private String region;

    private String key;

    private Integer min;
    private Integer max;
    private Integer live;
    private String nodeId;

    private boolean manual = false;
    private String manualScript;



    @PrePersist
    @PreUpdate
    public void defaults() {
        if (driver == null) {
            driver = ClusterDriver.MANUAL;
        }
        if (visibility == null) {
            visibility = ClusterVisibility.PRIVATE;
        }
        if (cloudType == null) {
            cloudType = ClusterCloud.OTHER;
        }
    }


}
