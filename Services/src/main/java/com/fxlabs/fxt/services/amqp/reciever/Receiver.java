package com.fxlabs.fxt.services.amqp.reciever;

import com.fxlabs.fxt.dto.git.GitTaskResponse;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.services.processors.receiver.GitTaskResponseProcessor;
import com.fxlabs.fxt.services.processors.receiver.RunTaskResponseProcessor;
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
    private RunTaskResponseProcessor processor;

    @Autowired
    private GitTaskResponseProcessor taskResponseProcessor;

    public void receiveMessage(BotTask task) {
        logger.info("Received Task [{}]", task.getId());
        processor.process(task);
    }

    public void receiveMessage(GitTaskResponse task) {
        logger.info("Received Task [{}]", task.getProjectId());
        taskResponseProcessor.process(task);
    }


}
