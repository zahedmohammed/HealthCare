package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.MarketplaceDataTask;
import com.fxlabs.fxt.services.project.MarketplaceDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class MarketPlaceProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private MarketplaceDataProvider marketplaceDataProvider;

    @Autowired
    public MarketPlaceProcessor(MarketplaceDataProvider marketplaceDataProvider) {

        this.marketplaceDataProvider = marketplaceDataProvider;
    }

    public MarketplaceDataTask process(MarketplaceDataTask task) {

        logger.info("Received MarketplaceDataTask with projectId [{}] and importName [{}]", task.getProjectId(), task.getImportName());
        return marketplaceDataProvider.get(task);

    }


}
