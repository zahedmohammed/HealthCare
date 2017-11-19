package com.fxlabs.fxt.services.processors;

import com.fxlabs.fxt.dao.repository.RunRepository;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.dto.run.RunTask;
import com.fxlabs.fxt.services.amqp.sender.BotClientService;
import com.fxlabs.fxt.services.run.RunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@Transactional
public class RunTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private RunRepository runRepository;

    @Autowired
    public RunTaskResponseProcessor(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    public void process(BotTask task) {
        logger.info("Task response [{}]...", task.getId());
        com.fxlabs.fxt.dao.entity.run.Run run = runRepository.findById(task.getId());
        com.fxlabs.fxt.dao.entity.run.RunTask runTask = run.getTask();

        // only if SUITE
        if ("SUITE".equals(task.getResult())) {
            runTask.setTotalSuiteCompleted(runTask.getTotalSuiteCompleted() + 1);
            runTask.setTotalTime(runTask.getTotalTime() + task.getRequestTime());
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
}
