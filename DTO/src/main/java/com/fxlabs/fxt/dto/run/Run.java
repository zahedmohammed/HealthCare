package com.fxlabs.fxt.dto.run;


import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.project.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Run extends BaseDto<String> {

    private Job job;

    private Long runId;

    private RunTask task;

    // override region, tags, env in reference Job entity.
    private Map<String, String> attributes = new HashMap<>();

    private String regions;

    private Map<String, Long> stats = new HashMap<>();

    private Integer validations;


    private String ciCdStatus;


    public String getCiCdStatus() {
        StringBuilder sb = new StringBuilder();
        double success = 0;
        try {
            long l = this.task.getTotalTests() - this.task.getFailedTests();
            success = ((l * 100)/ this.task.getTotalTests());
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
            sb.append(this.task.getStatus().toString()).append(":")
                    .append(success).append(":")
                    .append(this.task.getTotalTests()).append(":")
                    .append(this.task.getFailedTests()).append(":")
                    .append(this.task.getTimeTaken()).append(":")
                    .append(this.getRunId()).append(":")
                    .append("/#/app/projects/" + job.getProject().getId() + "/jobs/" + job.getId() + "/runs/" + runId).append(":")
                    .append("Bugs Logged = " + this.task.getIssuesLogged() + " Bugs Reopened = " + this.task.getIssuesReopen() +
                    " Bugs Closed = " + this.task.getIssuesClosed() + " Total Bugs = " + this.task.getTotalOpenIssues() );


        return sb.toString();
    }

}

