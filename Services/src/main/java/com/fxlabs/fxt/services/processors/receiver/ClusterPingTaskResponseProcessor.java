package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.dao.repository.jpa.ClusterPingRepository;
import com.fxlabs.fxt.dto.clusters.ClusterPing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class ClusterPingTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClusterPingRepository clusterPingRepository;

    public void process(ClusterPing task) {
        try {
            logger.info("Task response [{}]...", task.getBotId());

            // find by bot-id and update
            com.fxlabs.fxt.dao.entity.clusters.ClusterPing clusterPing = new com.fxlabs.fxt.dao.entity.clusters.ClusterPing();
            Optional<com.fxlabs.fxt.dao.entity.clusters.ClusterPing> clusterPingOptional = clusterPingRepository.findByBotId(task.getBotId());
            if (clusterPingOptional.isPresent()) {
                clusterPing = clusterPingOptional.get();
            }

            clusterPing.setBotId(task.getBotId());
            clusterPing.setKey(task.getKey());
            clusterPing.setTotalVBots(task.getTotalVBots());

            clusterPingRepository.save(clusterPing);


        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }
}
