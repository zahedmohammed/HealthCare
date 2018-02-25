package com.fxlabs.fxt.dto.run;

import com.fxlabs.fxt.dto.project.HttpMethod;
import com.fxlabs.fxt.dto.project.Policies;
import com.fxlabs.fxt.dto.project.TestCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class BotTask implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String projectDataSetId;
    private String suiteName;
    private String endpoint;
    private HttpMethod method;

    private String authType;
    private String username;
    private String password;

    private List<String> headers;
    private List<TestCase> testCases;

    private String response;

    private List<String> assertions;

    private String logs;

    private Date requestStartTime;
    private Date requestEndTime;
    private Long requestTime;

    private String result;

    private Long totalPassed = 0L;
    private Long totalFailed = 0L;
    private Long totalSkipped = 0L;
    private Long totalTests = 0L;


    private List<BotTask> init = new ArrayList<>();
    private List<BotTask> cleanup = new ArrayList<>();

    private Policies policies;

}
