package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.ProjectGitAccount;
import com.fxlabs.fxt.dao.entity.project.ProjectType;
import com.fxlabs.fxt.dao.entity.users.ProjectRole;
import com.fxlabs.fxt.dao.entity.users.ProjectUsers;
import com.fxlabs.fxt.dao.entity.users.SystemSetting;
import com.fxlabs.fxt.dao.entity.users.UsersPassword;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.git.GitTask;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
@Component
@Transactional
public class GaaSTaskRequestProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectGitAccountRepository projectGitAccountRepository;

    @Autowired
    private AmqpClientService amqpClientService;

    //@Autowired
    //private TextEncryptor encryptor;

    @Autowired
    private UsersPasswordRepository usersPasswordRepository;

    @Autowired
    private ProjectUsersRepository projectUsersRepository;

    @Value("${fx.gaas.queue.routingkey}")
    private String gaaSQueue;

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    /**
     * List projects of type GIT
     * Send as GitTask
     */
    public void process() {
        Stream<Project> projects = projectRepository.findByProjectTypeAndDeleted(ProjectType.GIT, false);
        projects.forEach(project -> {
            process(project);
        });
    }

    public void process(Project project) {
        try {
            Optional<ProjectGitAccount> gitAccount = projectGitAccountRepository.findByProjectId(project.getId());
            if (!gitAccount.isPresent()) {
                logger.warn("Ignoring Git sync for project with ID [{}] and Name [{}]", project.getId(), project.getName());
                return;
            }
            GitTask task = new GitTask();
            task.setProjectId(project.getId());
            task.setGitUrl(gitAccount.get().getUrl());
            task.setGitBranch(gitAccount.get().getBranch());
            task.setGitUsername(gitAccount.get().getUsername());
            if (!StringUtils.isEmpty(gitAccount.get().getPassword())) {
                task.setGitPassword(gitAccount.get().getPassword());
            }
            task.setGitLastCommit(gitAccount.get().getLastCommit());

            // Send Project-Users-Owner creds
            List<ProjectUsers> projectUsersList = projectUsersRepository.findByProjectIdAndRole(project.getId(), ProjectRole.OWNER);
            if (CollectionUtils.isEmpty(projectUsersList)) {
                logger.warn("Ignoring Git sync for project with ID [{}] with name [{}] because of no valid owner.", project.getId(), project.getName());
                return;
            }
            String ownerEmail = projectUsersList.get(0).getUsers().getEmail();
            Optional<UsersPassword> usersPasswordOptional = usersPasswordRepository.findByUsersEmailAndActive(ownerEmail, true);
            if (!usersPasswordOptional.isPresent()) {
                logger.warn("Ignoring Git sync for project with ID [{}] with name [{}] because of no valid owner is active.", project.getId(), project.getName());
                return;
            }

            task.setProjectUser(ownerEmail);
            task.setProjectGrant(usersPasswordOptional.get().getGrantKey());
            //TODO

            Optional<SystemSetting> systemSettingOptional = this.systemSettingRepository.findByKey("fx.base.url");
            if (systemSettingOptional.isPresent()) {
                task.setFxUrl(systemSettingOptional.get().getValue());
            } else {
                task.setFxUrl("http://localhost:8080");
            }

            amqpClientService.sendTask(task, gaaSQueue);
        } catch (RuntimeException ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }

}
