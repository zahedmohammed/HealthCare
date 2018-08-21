package com.fxlabs.fxt.services.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxlabs.fxt.converters.alerts.EventConverter;
import com.fxlabs.fxt.dao.repository.jpa.EventRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.events.Event;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Transactional(noRollbackFor = {Exception.class, IOException.class})
public class RemoteEventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventConverter eventConverter;

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private final Collection<EmitterWrapper> emitters = new ConcurrentLinkedQueue<>();

    public SseEmitter register(String org, String user) {

        logger.info("Register org [{}] user [{}]", org, user);
        SseEmitter emitter = new SseEmitter(604800_000L); // 7 days

        this.emitters.add(new EmitterWrapper(emitter, org, user));

        //emitter.onCompletion(() -> this.emitters.remove(emitter));
        //emitter.onTimeout(() -> this.emitters.remove(emitter));

        return emitter;

    }

    public void onEvent(Event event) {
        // TODO - persists event (DAO, Entity, Converter, Flyway)

        if (event == null) {
            return;
        }

        if (event.getEntityType() == null || StringUtils.isEmpty(event.getEntityType().toString())) {
            return;
        }

        if (event.getEventType() == null || StringUtils.isEmpty(event.getEventType().toString())) {
            return;
        }


        if (StringUtils.isEmpty(event.getEntityId())) {
            return;
        }

        if (StringUtils.isEmpty(event.getTaskId())) {
            return;
        }

        // TODO - check event exists and update
        Optional<com.fxlabs.fxt.dao.entity.event.Event> optionalEntity = eventRepository.findByEntityTypeAndEntityIdAndTaskId(com.fxlabs.fxt.dao.entity.event.Entity.valueOf(event.getEntityType().toString()), event.getEntityId(), event.getTaskId());

        if (optionalEntity.isPresent()) {
            com.fxlabs.fxt.dao.entity.event.Event entity = optionalEntity.get();
            entity.setStatus(com.fxlabs.fxt.dao.entity.event.Status.valueOf(event.getStatus().toString()));
            event = eventConverter.convertToDto(eventRepository.saveAndFlush(entity));
        } else {
            com.fxlabs.fxt.dao.entity.event.Event eventEntity = eventConverter.convertToEntity(event);
            event = eventConverter.convertToDto(eventRepository.saveAndFlush(eventEntity));
        }

        final Event event1 = event;

        logger.info("Event msg [{}] org [{}]", event.getName(), event.getOrg().getName());

        ObjectMapper mapperObj = new ObjectMapper();
        String jsonStr = null;
        try {
            jsonStr = mapperObj.writeValueAsString(event1);
        } catch (Exception ex) {

        }
        if (StringUtils.isEmpty(jsonStr)) {
            return;
        }

        final String jsonStr1 = jsonStr;

        List<EmitterWrapper> deadEmitters = new ArrayList<>();
        this.emitters.forEach(wrapper -> {
            try {
                if (StringUtils.equals(wrapper.getOrg(), event1.getOrg().getId())) {
                    wrapper.getSseEmitter().send("");
                }
            } catch (IOException e) {
                deadEmitters.add(wrapper);
                //logger.warn(e.getLocalizedMessage());
            } catch (Exception e) {
                deadEmitters.add(wrapper);
                //logger.warn(e.getLocalizedMessage());
            }
        });

        this.emitters.removeAll(deadEmitters);
    }

    class EmitterWrapper {
        private SseEmitter sseEmitter;
        private String org;
        private String user;

        public EmitterWrapper() {
        }

        public EmitterWrapper(SseEmitter sseEmitter, String org, String user) {
            this.sseEmitter = sseEmitter;
            this.org = org;
            this.user = user;
        }

        public SseEmitter getSseEmitter() {
            return sseEmitter;
        }

        public void setSseEmitter(SseEmitter sseEmitter) {
            this.sseEmitter = sseEmitter;
        }

        public String getOrg() {
            return org;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }


    public Response<List<Event>> getRecentOrgEvents(String orgId, Pageable pageable) {
        Date dt = DateUtils.addHours(Calendar.getInstance().getTime(), -8);
        Page<com.fxlabs.fxt.dao.entity.event.Event> page = eventRepository.findByOrgIdAndModifiedDateAfter(orgId, dt, pageable);
        return new Response<List<Event>>(eventConverter.convertToDtos(page.getContent()), page.getTotalElements(), page.getTotalPages());
    }

}
