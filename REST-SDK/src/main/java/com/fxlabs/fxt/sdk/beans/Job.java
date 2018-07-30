package com.fxlabs.fxt.sdk.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 *
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "Default";
    private String refId;
    private String description;

    private Environment environment;
    private List<String> tags;
    private String regions;

    private String cron;
    private Date nextFire;

    private boolean inactive;

    @JsonProperty("issueTracker")
    private JobIssueTracker issueTracker;

    @JsonProperty("notifications")
    private List<JobNotification> notifications;


}
