package com.fxlabs.fxt.services.events;

import com.fxlabs.fxt.dto.events.Event;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class RemoteEventService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private final CopyOnWriteArrayList<EmitterWrapper> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter register(String org, String user) {

        logger.info("Register org [{}] user [{}]", org, user);
        SseEmitter emitter = new SseEmitter(600_000L); // 10 minutes

        this.emitters.add(new EmitterWrapper(emitter, org, user));

        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));

        return emitter;

        // TODO - Send last 30 mins events pageSize 10 order by status (in_progress)
    }

    public void onEvent(Event event) {
        // TODO - persists event (DAO, Entity, Converter, Flyway)
        // TODO - check event exists and update
        logger.info("Event msg [{}] org [{}]", event.getName(), event.getOrg().getName());
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(wrapper -> {
            try {
                if (StringUtils.equals(wrapper.getOrg(), event.getOrg().getId())) {
                    wrapper.getSseEmitter().send(SseEmitter.event()
                            .id(event.getId())
                            .name(event.getName())
                            .data(event.getLink())
                            .reconnectTime(15_000L)
                            .build());
                }
            }
            catch (Exception e) {
                deadEmitters.add(wrapper.getSseEmitter());
            }
        });

        this.emitters.removeAll(deadEmitters);
    }

    class EmitterWrapper {
        private SseEmitter sseEmitter;
        private String org;
        private String user;

        public EmitterWrapper(){}

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

}
