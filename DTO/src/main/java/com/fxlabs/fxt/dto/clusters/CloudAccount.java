package com.fxlabs.fxt.dto.clusters;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CloudAccount extends BaseDto<String> {

    private String name;
    private String region;

    private String accessKey;
    private String secretKey;

    private NameDto org;

    private ClusterVisibility visibility;

    private ClusterCloud cloudType;

    private String nodeId;



}
