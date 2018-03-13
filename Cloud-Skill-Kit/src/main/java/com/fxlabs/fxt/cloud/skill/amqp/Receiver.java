package com.fxlabs.fxt.cloud.skill.amqp;


import com.fxlabs.fxt.cloud.skill.services.CloudDelegate;
import com.fxlabs.fxt.dto.cloud.CloudTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
public class Receiver {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CloudDelegate delegate;

    public void receiveMessage(CloudTask task) {
        logger.info("CloudTask id [{}]", task.getId());
        delegate.process(task);
    }

}