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

    private ClusterDriver driver;

    private ClusterStatus status;

    private ClusterVisibility visibility;

    private String name;
    private String region;
    private String key;


}
