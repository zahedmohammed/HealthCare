package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.ProjectMinimalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Job extends BaseDto<String> {

    private ProjectMinimalDto project;
    //private String projectId;

    private String name;
    private String refId;
    private String description;

    private Environment environment;

    private List<String> tags;

    private String regions;

    private String cron;

    private Date nextFire;

    private JobIssueTracker issueTracker = new JobIssueTracker();

    private List<JobNotification> notifications = new ArrayList<>();

    private String categories;

    private boolean notificationToDo;

    private boolean issueTrackerToDo;

    private long openIssues;
    private long closedIssues;

}

