package com.fxlabs.fxt.services.amqp.sender;

import com.fxlabs.fxt.dto.cloud.CloudTask;
import com.fxlabs.fxt.dto.notification.NotificationTask;
import com.fxlabs.fxt.dto.run.TestCaseResponse;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.dto.run.BotTask;
import com.fxlabs.fxt.dto.task.EmailTask;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface AmqpClientService {

    public void sendTask(BotTask task, String region);

    public void sendTask(VCTask task, String region);

    public void sendTask(EmailTask task, String region);

    public void sendTask(TestCaseResponse task, String region);

    void sendTask(CloudTask task, String region);

    public void sendTask(NotificationTask task, String region);
}
