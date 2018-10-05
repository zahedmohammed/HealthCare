package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.converters.project.EndpointConverter;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.users.ProjectRole;
import com.fxlabs.fxt.dao.repository.jpa.EndpointRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dto.alerts.*;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.events.Entity;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.dto.events.Status;
import com.fxlabs.fxt.dto.events.Type;
import com.fxlabs.fxt.dto.vc.VCTaskResponse;
import com.fxlabs.fxt.services.alerts.AlertService;
import com.fxlabs.fxt.services.events.LocalEventPublisher;
import com.fxlabs.fxt.services.project.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class GitTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AlertService alertService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private EndpointConverter endpointConverter;

    @Autowired
    private LocalEventPublisher localEventPublisher;

    public void process(VCTaskResponse task) {
        try {
            logger.info("Task response [{}]...", task.getProjectId());

            // if fail generate ERROR alert else INFO
            Alert alert = new Alert();
            alert.setRefId(task.getProjectId());
            alert.setRefType(AlertRefType.PROJECT);
            alert.setTaskType(TaskType.PROJECT_SYNC);
            alert.setTaskState(TaskState.ACTIVE);

            alert.setRefType(AlertRefType.PROJECT);

            if (task.isSuccess()) {
                alert.setType(AlertType.INFO);
            } else {
                alert.setType(AlertType.ERROR);
            }

            alert.setStatus(AlertStatus.UNREAD);
            alert.setSubject(task.getProjectName());

            alert.setMessage(StringUtils.removeAll(task.getLogs(), "\u0000"));

            List<String> users = new ArrayList<>();
            alert.setUsers(users);

            Optional<Project> optionalProject = projectRepository.findById(task.getProjectId());
            Project project =  null;
            if (optionalProject.isPresent()) {
                project = optionalProject.get();
                project.setAutoGenSuites(task.getAutoGenSuitesCount());
//                project.setApiEndpoints();

                projectRepository.save(project);

                endpointRepository.deleteAll(endpointRepository.findByProjectId(project.getId()));

                if (!CollectionUtils.isEmpty(task.getApiEndpoints())) {
                    endpointRepository.saveAll(endpointConverter.convertToEntities(task.getApiEndpoints()));
                }

                NameDto o = new NameDto();
                o.setId(project.getOrg().getId());
                alert.setOrg(o);
                alert.setRefName(project.getName());
            }

            Response<Alert> alert1 = alertService.save(alert);

            try {
                projectSyncEvent(project, Status.Done, Entity.Project, task.getTaskId(), alert1.getData().getId());
            } catch (Exception ex) {
                logger.warn("Exception sending project sync event");
            }


        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }


    public void projectSyncEvent(Project project, Status status, Entity entityType, String taskId, String logId) {

        if (project == null || status == null || entityType == null) {

            logger.info("Invalid event for project sync" );
            return;
        }


        Event event = new Event();
        //event.setId(project.getId());

        event.setTaskId(taskId);

        event.setName(project.getName());
        event.setUser(project.getCreatedBy());
        event.setEntityType(entityType);
        event.setEventType(Type.Sync);
        event.setEntityId(project.getId());
        event.setLink("/app/projects/" + project.getId() + "/test-suites");

        event.setStatus(status);
        NameDto org = new NameDto();
        org.setName(project.getOrg().getName());
        org.setId(project.getOrg().getId());
        event.setOrg(org);
        event.setLogId(logId);


        logger.info("Received response for publish on project [{}] and status [{}] for task type [{}]" , project.getId(), status.toString(), event.getName());
        localEventPublisher.publish(event);
    }
}
