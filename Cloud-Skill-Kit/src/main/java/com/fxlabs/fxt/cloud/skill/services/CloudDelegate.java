package com.fxlabs.fxt.cloud.skill.services;

import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.fxlabs.fxt.dto.cloud.CloudTaskType;
import com.fxlabs.fxt.cloud.skill.amqp.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CloudDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Sender sender;

    @Autowired
    private CloudService service;

    public void process(CloudTask task) {
        logger.info("CloudTask [{}]", task.getId());
        CloudTaskResponse response = null;
        try {
            // TODO call Create or Destroy
            if (task.getType() == CloudTaskType.CREATE) {
                response = service.create(task);
            } else if (task.getType() == CloudTaskType.DESTROY) {
                response = service.destroy(task);
            } else {
                // TODO handle invalid type
            }

            sender.sendTask(response);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }


}
