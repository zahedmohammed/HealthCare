package com.fxlabs.fxt.it.fx.skill;

import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.it.skill.services.IssueTrackerService;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;




/**
 * @author Mohammed Shoukath Ali
 */

@Component
public class FxIssueTrackerService implements IssueTrackerService {


    final Logger logger = LoggerFactory.getLogger(getClass());

    public ThreadLocal<StringBuilder> taskLogger = new ThreadLocal<>();



    @Override
    public ITTaskResponse process(TestCaseResponse task) {

        logger.info("In IT FxIssueTrackerService for project [{}]", task.getProject());
        ITTaskResponse response = new ITTaskResponse();
        taskLogger.set(new StringBuilder());
        taskLogger.get().append("FxIssueTracking System is under construction");

        response.setSuccess(true);
        response.setLogs(taskLogger.get().toString());

        if (StringUtils.isNotEmpty(task.getIssueId())) {
            //TODO update issue
            if (StringUtils.equals(task.getResult(), "pass")) {

                response.setIssueStatus("CLOSED");
            } else {
                response.setIssueStatus("OPEN");
            }

        } else {
            response.setIssueId(String.valueOf(RandomStringUtils.randomAlphabetic(20)));
            response.setIssueStatus("OPEN");
        }

        response.setTestCaseResponseId(task.getId());

        response.setRunId(task.getRunId());

        return response;
    }


}
