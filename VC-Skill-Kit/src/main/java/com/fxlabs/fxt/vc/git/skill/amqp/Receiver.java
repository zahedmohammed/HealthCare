package com.fxlabs.fxt.vc.git.skill.amqp;


import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.vc.git.skill.services.VCDelegate;
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
    private VCDelegate delegate;

    public void receiveMessage(VCTask task) {
        logger.info("VCTask id [{}]", task.getProjectId());
        delegate.process(task);
    }

}