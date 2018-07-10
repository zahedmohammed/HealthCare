package com.fxlabs.fxt.dao.entity.project;


import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 * @author Mohammed Shoukath Ali
 *
 */


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class JobNotification{


    private String name;
    private String channel;
    private String account = "Default_Slack";

    @Column(name="to_")
    private String to;
}
