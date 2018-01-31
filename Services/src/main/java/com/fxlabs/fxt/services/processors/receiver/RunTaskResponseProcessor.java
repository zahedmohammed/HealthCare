package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import com.fxlabs.fxt.dao.entity.run.TestSuiteResponse;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteResponseRepository;
import com.fxlabs.fxt.dao.repository.jpa.TestSuiteRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;
import com.fxlabs.fxt.dto.run.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Component
@Transactional
public class RunTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private RunRepository runRepository;
    private TestSuiteResponseRepository testSuiteResponseRepository;
    private TestSuiteRepository testSuiteRepository;
    private TestSuiteResponseESRepository testSuiteResponseESRepository;

    @Autowired
    public RunTaskResponseProcessor(RunRepository runRepository, TestSuiteResponseRepository dataSetRepository,
                                    TestSuiteRepository projectDataSetRepository, TestSuiteResponseESRepository testSuiteResponseESRepository) {
        this.runRepository = runRepository;
        this.testSuiteResponseRepository = dataSetRepository;
        this.testSuiteRepository = projectDataSetRepository;
        this.testSuiteResponseESRepository = testSuiteResponseESRepository;
    }

    //AtomicInteger i = new AtomicInteger(1);
    public void process(BotTask task) {
        try {
            //logger.info("Response {}", i.incrementAndGet());
            logger.info("Task response [{}]...", task.getId());
            // TODO - Replace this with job updating RunTask status
            com.fxlabs.fxt.dao.entity.run.Run run = runRepository.findByRunId(task.getId());
            com.fxlabs.fxt.dao.entity.run.RunTask runTask = run.getTask();

            // only if SUITE
            if ("SUITE".equals(task.getResult())) {
                //runTask.setTotalSuiteCompleted(runTask.getTotalSuiteCompleted() + 1);
                //runTask.setTotalTestCompleted(runTask.getTotalTestCompleted() + task.getTotalTests());
                //runTask.setSkippedTests(runTask.getSkippedTests() + task.getTotalSkipped());

                //runTask.setTotalTime(runTask.getTotalTime() + task.getRequestTime());

                saveDS(task, run);

            }
            // is complete?
            if (runTask.getTotalSuiteCompleted() >= runTask.getTotalTests()) {
                //runTask.setStatus(TaskStatus.COMPLETED);
                //runTask.setEndTime(new Date());
            }

            //runRepository.save(run);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
            //process(task);
        }
    }

    private void saveDS(BotTask task, com.fxlabs.fxt.dao.entity.run.Run run) {
        TestSuiteResponse ds = new TestSuiteResponse();
        ds.setRunId(run.getId());
        //TestSuite pds = new TestSuite();
        //pds.setId(task.getProjectDataSetId());
        //ds.setProjectDataSet(pds);
        Optional<TestSuite> testSuiteOptional = testSuiteRepository.findById(task.getProjectDataSetId());
        if (!testSuiteOptional.isPresent()) {
            logger.warn("Invalid Test-Suite Id");
        }
        TestSuite pds = testSuiteOptional.get();
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

        ds = this.testSuiteResponseRepository.save(ds);
        this.testSuiteResponseESRepository.save(ds);

    }
}