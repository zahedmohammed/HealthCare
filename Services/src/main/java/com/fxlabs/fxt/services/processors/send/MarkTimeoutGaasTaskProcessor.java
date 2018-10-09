package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.converters.alerts.EventConverter;
import com.fxlabs.fxt.dao.entity.event.Entity;
import com.fxlabs.fxt.dao.entity.event.Event;

import com.fxlabs.fxt.dao.entity.event.Status;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.es.TestSuiteResponseESRepository;
import com.fxlabs.fxt.dao.repository.jpa.AccountRepository;
import com.fxlabs.fxt.dao.repository.jpa.EventRepository;
import com.fxlabs.fxt.dao.repository.jpa.RunRepository;

import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.events.Type;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.events.LocalEventPublisher;
import com.fxlabs.fxt.services.notify.NotificationService;
import com.fxlabs.fxt.services.run.TestSuiteResponseService;
import org.apache.commons.lang3.time.DateUtils;

import org.jasypt.util.text.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
@Component
@Transactional
public class MarkTimeoutGaasTaskProcessor {

    private static final String COLON = " : ";
    public static final String LINE_SEPERATOR = "\n";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NotificationService notificationAccountService;

    @Autowired
    private AmqpClientService amqpClientService;

    @Value("${fx.notification.slack.queue.routingkey}")
    private String slackNotificationQueue;

    @Autowired
    private TextEncryptor encryptor;

    @Autowired
    private LocalEventPublisher localEventPublisher;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventConverter eventConverter;

    /**
     * Find project event with state 'Progress'
     * If timeout >= NOW
     * Mark task TIMEOUT or add to description
     */
    public void process() {
        Date dt = DateUtils.addMinutes(new Date(), -60);
        Date dt_ = DateUtils.addDays(new Date(), -10);
        Stream<Event> events = eventRepository.findByStatusAndEntityTypeAndCreatedDateBetween(Status.In_progress, Entity.Project, dt_, dt);

        events.forEach(event -> {
            try {
                event.setStatus(Status.Timeout);
                eventRepository.save(event);
                localEventPublisher.publish(eventConverter.convertToDto(event));
                //projectSyncEvent(event, com.fxlabs.fxt.dto.events.Status.Timeout, com.fxlabs.fxt.dto.events.Entity.valueOf(event.getEntityType().toString()), event.getTaskId());
            } catch (Exception ex) {
                logger.warn(ex.getLocalizedMessage(), ex);
            }
        });
    }


    public void projectSyncEvent(Event event_, com.fxlabs.fxt.dto.events.Status status, com.fxlabs.fxt.dto.events.Entity entityType, String taskId) {

        if (event_ == null || status == null || entityType == null) {

            logger.info("Invalid event for project sync");
            return;
        }


        com.fxlabs.fxt.dto.events.Event event = new com.fxlabs.fxt.dto.events.Event();
        //event.setId(project.getId());

        event.setTaskId(taskId);

        event.setName(event_.getName());
        event.setUser(event_.getCreatedBy());
        event.setEntityType(entityType);
        event.setEventType(Type.Sync);
        event.setEntityId(event_.getId());
        event.setLink(event_.getLink());

        event.setStatus(status);
        NameDto org = new NameDto();
        org.setName(event_.getOrg().getName());
        org.setId(event_.getOrg().getId());
        event.setOrg(org);

        logger.info("Sending event for publish on job [{}] and status [{}] for task type [{}]", event_.getId(), status.toString(), event.getEventType());
        localEventPublisher.publish(event);
    }

}
