package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import com.fxlabs.fxt.dao.repository.jpa.ClusterPingRepository;
import com.fxlabs.fxt.dao.repository.jpa.ClusterRepository;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.clusters.ClusterPing;
import com.fxlabs.fxt.dto.events.Entity;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.dto.events.Status;
import com.fxlabs.fxt.dto.events.Type;
import com.fxlabs.fxt.services.clusters.ClusterService;
import com.fxlabs.fxt.services.events.LocalEventPublisher;
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

    @Autowired
    private ClusterRepository clusterRepository;

    @Autowired
    private LocalEventPublisher localEventPublisher;

    public void process(ClusterPing task) {
        try {
            logger.info("Task response [{}]...", task.getBotId());

            // find by bot-id and update
            com.fxlabs.fxt.dao.entity.clusters.ClusterPing clusterPing = new com.fxlabs.fxt.dao.entity.clusters.ClusterPing();
            Optional<com.fxlabs.fxt.dao.entity.clusters.ClusterPing> clusterPingOptional = clusterPingRepository.findByBotId(task.getBotId());

            boolean firstPing = true;
            if (clusterPingOptional.isPresent()) {
                clusterPing = clusterPingOptional.get();
                firstPing = false;
            }

            clusterPing.setBotId(task.getBotId());
            clusterPing.setKey(task.getKey());
            clusterPing.setTotalVBots(task.getTotalVBots());

            com.fxlabs.fxt.dao.entity.clusters.ClusterPing clusterPing_ = clusterPingRepository.save(clusterPing);
            if (firstPing) {
                Optional<Cluster> optionalCluster = clusterRepository.findByKey(task.getKey());
                if (optionalCluster.isPresent()) {
                    try {
                        botPingEvent(optionalCluster.get(), Status.Done, Entity.Bot, clusterPing_.getId(), null, Type.Connected);
                    } catch (Exception e) {
                        logger.info(e.getLocalizedMessage());
                    }
                }
            }

        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }


    public void botPingEvent(Cluster dto, Status status, Entity entityType, String taskId, String logId, Type type) {

        if (dto == null || status == null || entityType == null) {

            logger.info("Invalid event for Bot creation" );
            return;
        }


        Event event = new Event();
        //event.setId(project.getId());

        event.setTaskId(taskId);

        event.setName(dto.getName());
        event.setUser(dto.getCreatedBy());
        event.setEntityType(entityType);
        event.setEventType(type);
        event.setEntityId(dto.getId());
        event.setLink("/app/regions/" + dto.getId());

        event.setStatus(status);
        NameDto org = new NameDto();
        org.setName(dto.getOrg().getName());
        org.setId(dto.getOrg().getId());
        event.setOrg(org);
        event.setLogId(logId);


        logger.info("Received response for  bot [{}] and status [{}] for task type [{}]" , dto.getId(), status.toString(), event.getName());
        localEventPublisher.publish(event);
    }
}
