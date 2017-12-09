package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import com.fxlabs.fxt.dao.repository.TestSuiteResponseRepository;
import com.fxlabs.fxt.dao.repository.TestSuiteRepository;
import com.fxlabs.fxt.dao.repository.RunRepository;
import com.fxlabs.fxt.dto.run.BotTask;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Transactional
public class RunTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private RunRepository runRepository;
    private TestSuiteResponseRepository testSuiteResponseRepository;
    private TestSuiteRepository testSuiteRepository;

    @Autowired
    public RunTaskResponseProcessor(RunRepository runRepository, TestSuiteResponseRepository dataSetRepository, TestSuiteRepository projectDataSetRepository) {
        this.runRepository = runRepository;
        this.testSuiteResponseRepository = dataSetRepository;
        this.testSuiteRepository = projectDataSetRepository;
    }

    AtomicInteger i = new AtomicInteger(1);
    public void process(BotTask task) {
        logger.info("Response {}", i.incrementAndGet());
        logger.info("Task response [{}]...", task.getId());
        com.fxlabs.fxt.dao.entity.run.Run run = runRepository.findById(task.getId());
        com.fxlabs.fxt.dao.entity.run.RunTask runTask = run.getTask();

        // only if SUITE
        if ("SUITE".equals(task.getResult())) {
            runTask.setTotalSuiteCompleted(runTask.getTotalSuiteCompleted() + 1);
            runTask.setTotalTestCompleted(runTask.getTotalTestCompleted() + task.getTotalTests());
            //runTask.setFailedTests(runTask.getFailedTests() + task.getTotalFailed());
            runTask.setSkippedTests(runTask.getSkippedTests() + task.getTotalSkipped());

            runTask.setTotalTime(runTask.getTotalTime() + task.getRequestTime());

            saveDS(task, run);

        }
        // is complete?
        if (runTask.getTotalSuiteCompleted() >= runTask.getTotalTests()) {
            runTask.setStatus(TaskStatus.COMPLETED);
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
        TestSuite pds = testSuiteRepository.findOne(task.getProjectDataSetId());
        ds.setTestSuite(pds.getName());
        ds.setLogs(task.getLogs());
        ds.setResponse(task.getResponse());

        ds.setRequestEndTime(task.getRequestStartTime());
        ds.setRequestEndTime(task.getRequestEndTime());
        ds.setRequestTime(task.getRequestTime());

        ds.setTotalPassed(task.getTotalPassed());
        ds.setTotalFailed(task.getTotalFailed());
        //ds.setTotalSkipped(task.getTotalSkipped());

        ds.setStatus(task.getResult());

        this.testSuiteResponseRepository.save(ds);

    }
}
