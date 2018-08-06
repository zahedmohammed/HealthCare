package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.event.Entity;
import com.fxlabs.fxt.dao.entity.event.Event;
import com.fxlabs.fxt.dao.entity.event.Type;
import org.hibernate.type.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.event.DocumentEvent;
import java.util.Optional;

/**
 * @author Mohammed Shoukath Ali
 */
public interface EventRepository extends JpaRepository<Event, String> {


    Optional<Event> findByEntityTypeAndEventTypeAndEntityId(Entity entityType, Type type, String entityId);
}
