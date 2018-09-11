package com.fxlabs.fxt.dto.it;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ITTaskResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String issueId;
    private String issueStatus;
    private String projectName;

    private String logs;
    private boolean success;

    private String testCaseResponseId;
    private String runId;

}
