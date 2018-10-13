package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Mohammed Luqman Shareef
 * @since 7/4/2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProjectSaving implements Serializable{

    private String name;
    private String id;

    private Integer autoGenAllSuites;
    private Integer autoGenSecuritySuites;

    private Long totalTimeSaving;
    private Long totalCostSaving;
    private Long securityCoverageTimeSaving;
    private Long securityCoverageCostSaving;

}

