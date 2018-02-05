package com.fxlabs.fxt.dto.clusters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClusterPing implements Serializable {

    private static final long serialVersionUID = 1L;

    private String botId;
    private String key;
    private Integer totalVBots;

}
