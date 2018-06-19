package com.fxlabs.fxt.bot.processor;

import com.fxlabs.fxt.dto.project.DataRecord;
import com.fxlabs.fxt.dto.project.MarketplaceDataTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DataCache {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final InheritableThreadLocal<MarketplaceDataTask> task = new InheritableThreadLocal<>();
    private static final InheritableThreadLocal<List<DataRecord>> records = new InheritableThreadLocal<>();
    private static final InheritableThreadLocal<AtomicInteger> pointer = new InheritableThreadLocal<>();
    private static final InheritableThreadLocal<Boolean> inUse = new InheritableThreadLocal<>();

    @Autowired
    private MarketplaceDataReader reader;

    public Long init(String projectId, String importName, String policy) {
        try {
            MarketplaceDataTask marketplaceDataTask = new MarketplaceDataTask();

            marketplaceDataTask.setPolicy(policy);
            marketplaceDataTask.setCurrentPage(0);
            marketplaceDataTask.setProjectId(projectId);
            marketplaceDataTask.setImportName(importName);

            logger.info("Reading page [{}] module [{}] for project [{}]", marketplaceDataTask.getCurrentPage(), importName, projectId);
            marketplaceDataTask = reader.get(marketplaceDataTask);

            task.set(marketplaceDataTask);

            records.set(marketplaceDataTask.getRecords());
            pointer.set(new AtomicInteger(0));

            this.inUse.set(Boolean.TRUE);

            return task.get().getTotalElements();
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
            return 0L;
        }
    }

    public boolean isEmpty() {
        try {
            ensureData();
            if (!inUse.get()) {
                return true;
            }
            logger.info("records [{}]", records.get() != null ? records.get().size() : 0);
            logger.info("pointer [{}]", pointer.get() != null ? pointer.get().get() : 0);
            return records.get().size() <= pointer.get().get();
        } catch (Exception e) {
            return true;
        }
    }

    public MarketplaceDataTask get(String projectId, String importName) {
        if (isEmpty()) {
            return null;
        }
        task.get().setEval(records.get().get(pointer.get().getAndIncrement()).getRecord());

        return task.get();
    }

    private synchronized void ensureData() {
        try {
            // pointer >= size && currentPage * 100 < totalElements
            if (inUse.get() && (pointer.get().get() >= records.get().size()) && (task.get().getCurrentPage() + 1) * 100 < task.get().getTotalElements()) {

                MarketplaceDataTask marketplaceDataTask = task.get();

                marketplaceDataTask.setCurrentPage(marketplaceDataTask.getCurrentPage() + 1);

                logger.info("Reading page [{}] module [{}] for project [{}]", marketplaceDataTask.getCurrentPage(), marketplaceDataTask.getImportName(), marketplaceDataTask.getProjectId());
                marketplaceDataTask = reader.get(task.get());

                task.set(marketplaceDataTask);

                records.set(marketplaceDataTask.getRecords());
                pointer.set(new AtomicInteger(0));
            }
        } catch (Exception e) {
            logger.warn(e.getLocalizedMessage());
        }
    }


}
