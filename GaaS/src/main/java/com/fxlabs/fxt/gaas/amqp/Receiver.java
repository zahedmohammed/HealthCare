package com.fxlabs.fxt.gaas.amqp;


import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.gaas.services.GitDelegate;
import com.fxlabs.fxt.gaas.services.GitService;
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
    private GitDelegate delegate;

    public void receiveMessage(GitTask task) {
        logger.info("GitTask id [{}]", task.getProjectId());
        delegate.process(task);
    }

}