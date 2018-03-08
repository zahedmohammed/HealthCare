package com.fxlabs.fxt.services.amqp.reciever;

import com.fxlabs.fxt.dto.clusters.ClusterPing;
import com.fxlabs.fxt.dto.it.ITTaskResponse;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.dto.vc.VCTaskResponse;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.Suite;
import com.fxlabs.fxt.services.processors.receiver.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class Receiver {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RunTaskResponseProcessor processor;

    @Autowired
    private GitTaskResponseProcessor taskResponseProcessor;

    @Autowired
    private SuiteResponseProcessor suiteResponseProcessor;

    @Autowired
    private ClusterPingTaskResponseProcessor clusterPingTaskResponseProcessor;

    @Autowired
    private IssueTrackerTaskResponseProcessor issueTrackerTaskResponseProcessor;

    @Autowired
    private TestCaseResponseProcessor testCaseResponseProcessor;

    public void receiveMessage(BotTask task) {
        logger.info("Received BotTask [{}]", task.getId());
        processor.process(task);
    }

    public void receiveMessage(VCTaskResponse task) {
        logger.info("Received VCTaskResponse [{}]", task.getProjectId());
        taskResponseProcessor.process(task);
    }

    public void receiveMessage(ClusterPing task) {
        logger.info("Received ClusterPing [{}]", task.getBotId());
        clusterPingTaskResponseProcessor.process(task);
    }

    public void receiveMessage(Suite suite) {
        logger.info("Received Suite [{}]", suite.getSuiteName());
        suiteResponseProcessor.process(suite);
    }

    public void receiveMessage(List<TestCaseResponse> testCaseResponses) {
        logger.info("Received TestCaseResponse count [{}]", testCaseResponses.size());
        testCaseResponseProcessor.process(testCaseResponses);
    }

    public void receiveMessage(ITTaskResponse issueTrackerTaskResponse) {
        logger.info("Received TestCaseResponse count [{}]", issueTrackerTaskResponse.getProjectName());
        issueTrackerTaskResponseProcessor.process(issueTrackerTaskResponse);
    }


}
