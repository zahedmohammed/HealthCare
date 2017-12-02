package com.fxlabs.fxt.services.processors;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import com.fxlabs.fxt.dao.repository.TestSuiteResponseRepository;
import com.fxlabs.fxt.dao.repository.TestSuiteRepository;
import com.fxlabs.fxt.dao.repository.RunRepository;
import com.fxlabs.fxt.dto.run.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Transactional
public class RunTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private RunRepository runRepository;
    private TestSuiteResponseRepository dataSetRepository;
    private TestSuiteRepository projectDataSetRepository;

    @Autowired
    public RunTaskResponseProcessor(RunRepository runRepository, TestSuiteResponseRepository dataSetRepository, TestSuiteRepository projectDataSetRepository) {
        this.runRepository = runRepository;
        this.dataSetRepository = dataSetRepository;
        this.projectDataSetRepository = projectDataSetRepository;
    }

    public void process(BotTask task) {
        logger.info("Task response [{}]...", task.getId());
        com.fxlabs.fxt.dao.entity.run.Run run = runRepository.findById(task.getId());
        com.fxlabs.fxt.dao.entity.run.RunTask runTask = run.getTask();

        // only if SUITE
        if ("SUITE".equals(task.getResult())) {
            runTask.setTotalSuiteCompleted(runTask.getTotalSuiteCompleted() + 1);
            runTask.setFailedTests(runTask.getFailedTests() + task.getTotalFailed());
            runTask.setSkippedTests(runTask.getSkippedTests() + task.getTotalSkipped());

            runTask.setTotalTime(runTask.getTotalTime() + task.getRequestTime());
            saveDS(task, run);
        } else {

            runTask.setTotalTestCompleted(runTask.getTotalTestCompleted() + 1);
            logger.info("Test result [{}]", task.getResult());
            if ("fail".equals(task.getResult())) {
                runTask.setFailedTests(runTask.getFailedTests() + 1);
            } else if ("skip".equals(task.getResult())) {
                runTask.setFailedTests(runTask.getSkippedTests() + 1);
            }

        }
        // is complete?
        if (runTask.getTotalSuiteCompleted() >= runTask.getTotalTests()) {
            runTask.setStatus("Completed!");
            runTask.setEndTime(new Date());
        }

        runRepository.save(run);
    }

    private void saveDS(BotTask task, com.fxlabs.fxt.dao.entity.run.Run run) {
        TestSuiteResponse ds = new TestSuiteResponse();
        ds.setRunId(run.getId());
        //TestSuite pds = new TestSuite();
        //pds.setId(task.getProjectDataSetId());
        //ds.setProjectDataSet(pds);
        TestSuite pds = projectDataSetRepository.findOne(task.getProjectDataSetId());
        ds.setTestSuite(pds.getName());
        ds.setLogs(task.getLogs());
        ds.setResponse(task.getResponse());

        ds.setRequestEndTime(task.getRequestStartTime());
        ds.setRequestEndTime(task.getRequestEndTime());
        ds.setRequestTime(task.getRequestTime());

        ds.setTotalPassed(task.getTotalPassed());
        ds.setTotalFailed(task.getTotalFailed());
        ds.setTotalSkipped(task.getTotalSkipped());

        ds.setStatus(task.getResult());

        this.dataSetRepository.save(ds);

    }
}
