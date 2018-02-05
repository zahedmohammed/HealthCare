package com.fxlabs.fxt.services.processors.receiver;

import com.fxlabs.fxt.dao.entity.users.ProjectRole;
import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import com.fxlabs.fxt.dao.repository.jpa.ProjectUsersRepository;
import com.fxlabs.fxt.dto.alerts.*;
import com.fxlabs.fxt.dto.git.GitTaskResponse;
import com.fxlabs.fxt.services.alerts.AlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class GitTaskResponseProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectUsersRepository projectUsersRepository;

    @Autowired
    private AlertService alertService;

    public void process(GitTaskResponse task) {
        try {
            logger.info("Task response [{}]...", task.getProjectId());

            // if fail generate ERROR alert else INFO
            Alert alert = new Alert();
            alert.setRefId(task.getProjectId());
            alert.setRefType(AlertRefType.PROJECT);
            alert.setState(AlertState.ACTIVE);

            if (task.isSuccess()) {
                alert.setSeverity(AlertSeverity.SUCCESS);
                alert.setType(AlertType.PROJECT_SYNC_SUCCESS); // TODO
            } else {
                alert.setSeverity(AlertSeverity.ERROR); // TODO
                alert.setType(AlertType.PROJECT_SYNC_FAILURE); // TODO
            }

            alert.setStatus(AlertStatus.UNREAD);
            alert.setSubject("Project Git Pull");

            alert.setMessage(task.getLogs());
            List<String> users = new ArrayList<>();
            alert.setUsers(users);

            // find project-owners
            List<ProjectUsers> projectUsersList = projectUsersRepository.findByProjectIdAndRole(task.getProjectId(), ProjectRole.OWNER);
            if (!CollectionUtils.isEmpty(projectUsersList)) {
                projectUsersList.stream().forEach(pu -> {
                    users.add(pu.getUsers().getId());
                    alert.setRefName(pu.getProject().getName());
                });
            }

            alertService.save(alert);


        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }
}
