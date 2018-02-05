package com.fxlabs.fxt.dao.entity.clusters;

import com.fxlabs.fxt.dao.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author Intesar Shannan Mohammed
 */
//@Document(indexName = "fxclusterping")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClusterPing extends BaseEntity {

    private String botId;
    private String key;
    private Integer totalVBots;


}
