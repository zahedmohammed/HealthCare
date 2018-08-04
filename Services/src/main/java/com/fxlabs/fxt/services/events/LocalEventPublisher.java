package com.fxlabs.fxt.services.events;

import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LocalEventPublisher {

    @Autowired
    private AmqpClientService amqpClientService;

    /**
     * type: sync | run | deployment
     * entity: Project -> sync
     *         Job     -> run
     *         Bot     -> deploy
     *
     *  entity attributes:
     *     type (Project, Job, Bot),
     *     action (Sync, Run, Deploy),
     *     id,
     *     link:
     *     name, ABC/Default/#13
     *     status (in-progress/completed),
     *     start-dt, end-dt, user
     *
     *     e.g. msgs:
     *       - ABC's Sync In_progress
     *       - ABC's Sync Completed
     *       - ABC/Default/#13 In_progress
     *       - ABC/Default/#13 Completed
     *
     */
    public void publish(Event event) {
        amqpClientService.sendEvent(event);
    }


    // TODO - Remove it - only for testing.
    @Scheduled(fixedRate = 15000)
    public void doSomething() {
        Event event = new Event();
        event.setId(RandomStringUtils.random(13));
        event.setName("Project sync");
        event.setLink("/projects");
        NameDto org = new NameDto();
        org.setName("Default");
        org.setId("4028b881620688c001620689a3210000");
        event.setOrg(org);
        event.setUser("Administrator");
        publish(event);
    }

}
