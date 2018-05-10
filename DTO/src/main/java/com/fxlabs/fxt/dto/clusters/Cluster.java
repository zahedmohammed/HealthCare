package com.fxlabs.fxt.dto.clusters;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cluster extends BaseDto<String> {

    private NameDto org;
    private CloudAccount cloudAccount;

    private ClusterDriver driver;

    private ClusterStatus status;

    private ClusterVisibility visibility;

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

}
