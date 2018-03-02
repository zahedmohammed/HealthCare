package com.fxlabs.fxt.it.skill.amqp;


import com.fxlabs.fxt.dto.it.ITTask;
import com.fxlabs.fxt.it.skill.services.ITDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
@Component
public class Receiver {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ITDelegate delegate;

    public void receiveMessage(ITTask task) {
        logger.info("IssueTrackerTask id [{}]", task.getProjectId());
        delegate.process(task);
    }

}