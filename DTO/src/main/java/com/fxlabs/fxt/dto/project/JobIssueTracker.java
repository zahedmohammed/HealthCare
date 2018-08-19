package com.fxlabs.fxt.dto.project;


import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.clusters.AccountType;
import lombok.*;

import java.io.Serializable;

/**
 * @author Mohammed Shoukath Ali
 *
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class JobIssueTracker implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String url;
    private String projectKey;
    private String account = "Default_GitHub";
    private AccountType accountType;


}
