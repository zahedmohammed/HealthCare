package com.fxlabs.fxt.services.processors.send;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mohammed Shoukath Ali
 */
@DisallowConcurrentExecution
public class MarkTimeoutGaasTaskJob implements org.quartz.Job {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MarkTimeoutGaasTaskProcessor processor;
    /**
     *
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("MarkTimeoutGaasTaskJob run....");
        processor.process();
    }

}
