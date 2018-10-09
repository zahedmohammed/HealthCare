package com.fxlabs.fxt.cloud.skill.services;

import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.cloud.CloudTaskResponse;
import com.fxlabs.fxt.dto.cloud.CloudTaskType;
import com.fxlabs.fxt.cloud.skill.amqp.Sender;
import com.fxlabs.fxt.dto.cloud.PingTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CloudDelegate {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Sender sender;

    @Autowired
    @Qualifier("awsCloudService")
    private CloudService awsCloudService;

    @Autowired
    @Qualifier("azureCloudService")
    private CloudService azureCloudService;

    public void process(CloudTask task) {
        logger.info("CloudTask [{}]", task.getId());
        CloudTaskResponse response = null;
        try {

            switch (task.getCloudType()) {
                case AWS:
                    response = getAWSCloudTaskResponse(task, response);
                    break;
                case AZURE:
                    response = getAzureCloudTaskResponse(task, response);
                    break;
                default:
                    logger.info("CloudType [{}] not supported for CloudTask [{}]", task.getCloudType().toString(), task.getId());

            }

            sender.sendTask(response);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }

    private CloudTaskResponse getAWSCloudTaskResponse(CloudTask task, CloudTaskResponse response) {
        // TODO call Create or Destroy
        if (task.getType() == CloudTaskType.CREATE) {
            response = awsCloudService.create(task);
        } else if (task.getType() == CloudTaskType.DESTROY) {
            response = awsCloudService.destroy(task);
        } else {
            // TODO handle invalid type
        }
        return response;
    }


    private CloudTaskResponse getAzureCloudTaskResponse(CloudTask task, CloudTaskResponse response) {
        // TODO call Create or Destroy
        if (task.getType() == CloudTaskType.CREATE) {
            response = azureCloudService.create(task);
        } else if (task.getType() == CloudTaskType.DESTROY) {
            response = azureCloudService.destroy(task);
        } else {
            // TODO handle invalid type
        }
        return response;
    }

    public String ping(PingTask pingTask) {
        logger.info("PingTask received...");
        return "Ok!";
    }


}
