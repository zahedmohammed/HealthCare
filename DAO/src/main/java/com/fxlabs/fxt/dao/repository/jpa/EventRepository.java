package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.alerts.Alert;
import com.fxlabs.fxt.dao.entity.event.Entity;
import com.fxlabs.fxt.dao.entity.event.Event;
import com.fxlabs.fxt.dao.entity.event.Status;
import com.fxlabs.fxt.dao.entity.event.Type;
import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import org.hibernate.type.EntityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.event.DocumentEvent;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface EventRepository extends JpaRepository<Event, String> {


    Optional<Event> findByEntityTypeAndEntityIdAndTaskId(Entity entityType, String entityId, String taskId);

    Page<Event> findByOrgIdAndModifiedDateAfter(String orgId, Date dt, Pageable pageable);

    Stream<Event> findByStatusAndEntityTypeAndCreatedDateLessThan(Status status, Entity entityType, Date dt);


}
