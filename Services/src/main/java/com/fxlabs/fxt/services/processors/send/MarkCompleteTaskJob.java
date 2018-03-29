package com.fxlabs.fxt.services.processors.send;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@DisallowConcurrentExecution
public class MarkCompleteTaskJob implements org.quartz.Job {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MarkCompleteTaskProcessor processor;


    /**
     *
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        processor.process();
    }

}
