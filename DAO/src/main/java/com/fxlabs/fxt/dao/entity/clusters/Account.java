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
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
@Document(indexName = "fx-accounts")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    private String name;
    private String region;

    @Enumerated(EnumType.STRING)
    private ClusterVisibility visibility;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private ClusterCloud cloudType;

    private String accessKey;
    private String secretKey;

    @PrePersist
    @PreUpdate
    public void defaults() {
        if (visibility == null) {
            visibility = ClusterVisibility.PRIVATE;
        }
        if (cloudType == null) {
            cloudType = ClusterCloud.OTHER;
        }
    }

}
