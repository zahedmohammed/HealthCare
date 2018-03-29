package com.fxlabs.fxt.services.processors.send;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Intesar Shannan Mohammed
 */
@DisallowConcurrentExecution
public class SendRequestJob implements Job {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RunTaskRequestProcessor processor;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Job run....");
        processor.process();
    }
}
