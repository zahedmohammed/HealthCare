package com.fxlabs.fxt.services.amqp.sender;

import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.dto.run.BotTask;

/**
 * @author Intesar Shannan Mohammed
 */
public interface AmqpClientService {

    public void sendTask(BotTask task, String region);

    public void sendTask(GitTask task, String region);
}
