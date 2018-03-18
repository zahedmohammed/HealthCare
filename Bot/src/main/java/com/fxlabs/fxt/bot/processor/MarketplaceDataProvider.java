package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.bot.amqp.Sender;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.MarketplaceDataTask;
import org.springframework.stereotype.Component;

@Component
public class MarketplaceDataProvider {

    private Sender sender;
    public MarketplaceDataProvider(Sender sender) {
        this.sender = sender;
    }

    public MarketplaceDataTask get(String projectId, String importName) {
        MarketplaceDataTask task = new MarketplaceDataTask();
        task.setProjectId(projectId);
        task.setImportName(importName);
        return sender.processMarketplaceRequest(task);
    }
}
