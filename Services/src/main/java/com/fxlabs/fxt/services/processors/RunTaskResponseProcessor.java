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

        runTask.setTotalTestCompleted(runTask.getTotalTestCompleted() + 1);

        runTask.setTotalTime(runTask.getTotalTime() + (task.getRequestEndTime().getTime() - task.getRequestStartTime().getTime()));

        if (!task.getSuccess())
            runTask.setFailedTests(runTask.getFailedTests() + 1);

        // is complete?
        if (runTask.getTotalTestCompleted() >= runTask.getTotalTests()) {
            runTask.setStatus("Completed!");
            runTask.setEndTime(new Date());
        }

        runRepository.save(run);
    }
}
