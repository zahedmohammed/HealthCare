package com.fxlabs.fxt.dao.entity.notify;

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
@Document(indexName = "fxnotificationaccount")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotificationAccount extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "org_id")
    private Org org;

    private String name;
    private String region;

    @Enumerated(EnumType.STRING)
    private NotificationVisibility visibility;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String accessKey;
    private String secretKey;

    private String token;
    private String channel;


    @PrePersist
    @PreUpdate
    public void defaults() {
        if (visibility == null) {
            visibility = NotificationVisibility.PRIVATE;
        }
//        if (type == null) {
//            type = NotificationType.EMAIL;
//        }
    }

}
