package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.clusters.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

