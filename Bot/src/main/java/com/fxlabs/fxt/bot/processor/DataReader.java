package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.dto.project.MarketplaceDataTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataReader {

    @Autowired
    private DataCache cache;

    @Autowired
    private MarketplaceDataReader reader;

    public MarketplaceDataTask get(String projectId, String importName) {
        MarketplaceDataTask task = cache.get(projectId, importName);

        if (task != null) {
            return task;
        }

        return reader.get(projectId, importName);
    }
}
