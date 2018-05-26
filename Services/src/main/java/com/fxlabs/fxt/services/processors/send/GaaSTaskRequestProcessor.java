package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.users.SystemSetting;
import com.fxlabs.fxt.dao.entity.users.UsersPassword;
import com.fxlabs.fxt.dao.repository.jpa.AccountRepository;
import com.fxlabs.fxt.dao.repository.jpa.ProjectRepository;
import com.fxlabs.fxt.dao.repository.jpa.SystemSettingRepository;
import com.fxlabs.fxt.dao.repository.jpa.UsersPasswordRepository;
import com.fxlabs.fxt.dto.project.GenPolicy;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private AmqpClientService amqpClientService;

    //@Autowired
    //private TextEncryptor encryptor;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UsersPasswordRepository usersPasswordRepository;

    @Value("${fx.gaas.queue.routingkey}")
    private String gaaSQueue;

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    /**
     * List projects of type GIT
     * Send as VCTask
     */
    public void process() {
        Stream<Project> projects = projectRepository.findByInactive(false);
        projects.forEach(project -> {
            process(project);
        });
    }

    public void process(Project project) {
        try {
            VCTask task = new VCTask();
            task.setProjectId(project.getId());
            task.setProjectName(project.getName());
            if (project.getGenPolicy() != null && task.getGenPolicy() != GenPolicy.None) {
                task.setGenPolicy(GenPolicy.valueOf(project.getGenPolicy().name()));
                task.setOpenAPISpec(project.getOpenAPISpec());
            }
            task.setVcUrl(project.getUrl());
            task.setVcBranch(project.getBranch());

            if (project.getAccount() != null) {
                Optional<Account> accountOptional = accountRepository.findById(project.getAccount().getId());
                Account account = accountOptional.isPresent() ? accountOptional.get() : null;
                task.setVcUsername(account.getAccessKey());
                task.setVcPassword(account.getSecretKey());
            }
            task.setVcLastCommit(project.getLastCommit());


            String ownerEmail = null;//projectUsersList.get(0).getUsers().getEmail();
            Optional<UsersPassword> usersPasswordOptional = usersPasswordRepository.findByUsersEmailAndActive(ownerEmail, true);
            if (!usersPasswordOptional.isPresent()) {
                logger.warn("Ignoring Git sync for project with ID [{}] with name [{}] because of no valid owner is active.", project.getId(), project.getName());
                return;
            }

            task.setProjectUser(ownerEmail);
            //task.setProjectGrant(usersPasswordOptional.get().getGrantKey());
            //TODO

            Optional<SystemSetting> systemSettingOptional = this.systemSettingRepository.findByKey("fx.base.url");
            if (systemSettingOptional.isPresent()) {
                task.setFxUrl(systemSettingOptional.get().getValue());
            } else {
                task.setFxUrl("http://fx-control-plane:8080");
            }

            amqpClientService.sendTask(task, gaaSQueue);
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }

}
