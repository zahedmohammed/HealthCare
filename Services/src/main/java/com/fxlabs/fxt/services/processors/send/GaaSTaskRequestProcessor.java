package com.fxlabs.fxt.services.processors.send;

import com.fxlabs.fxt.converters.project.AutoCodeConfigConverter;
import com.fxlabs.fxt.converters.project.AutoCodeConfigMinimalConverter;
import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.users.SystemSetting;
import com.fxlabs.fxt.dao.entity.users.UsersPassword;
import com.fxlabs.fxt.dao.repository.jpa.*;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.events.Entity;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.dto.events.Status;
import com.fxlabs.fxt.dto.events.Type;
import com.fxlabs.fxt.dto.project.*;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.dto.vc.VCTask;
import com.fxlabs.fxt.services.amqp.sender.AmqpClientService;
import com.fxlabs.fxt.services.events.LocalEventPublisher;
import com.fxlabs.fxt.services.users.UsersService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.EntityType;
import org.jasypt.util.text.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    private UsersService usersService;

    @Autowired
    private AmqpClientService amqpClientService;

    @Autowired
    private TextEncryptor encryptor;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UsersPasswordRepository usersPasswordRepository;

    @Value("${fx.gaas.queue.routingkey}")
    private String gaaSQueue;

    @Autowired
    private SystemSettingRepository systemSettingRepository;

    @Autowired
    private AutoCodeConfigMinimalConverter autoCodeConfigMinimalConverter;

    @Autowired
    private AutoCodeConfigConverter autoCodeConfigConverter;

    @Autowired
    private LocalEventPublisher localEventPublisher;

    @Autowired
    private AutoCodeConfigRepository autoCodeConfigRepository;

    /**
     * List projects of type GIT
     * Send as VCTask
     */
    public void process() {
        Stream<Project> projects = projectRepository.findByInactive(false);
        projects.forEach(project -> {
            process(project, null);
        });
    }

    public void process(Project project, ProjectSync projectSync) {
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

            if (project.getAccount() != null && !StringUtils.isEmpty(project.getAccount().getId())) {
                Optional<Account> accountOptional = accountRepository.findById(project.getAccount().getId());
                Account account = accountOptional.isPresent() ? accountOptional.get() : null;
                task.setVcUsername(account.getAccessKey());
                if (StringUtils.isNotEmpty(account.getSecretKey())) {
                    task.setVcPassword(encryptor.decrypt(account.getSecretKey()));
                }
            }
            task.setVcLastCommit(project.getLastCommit());

            //Set AutoCode configurations
            Optional<com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig> codeConfigOptional = autoCodeConfigRepository.findByProjectId(project.getId());
            if (codeConfigOptional.isPresent()){
                AutoCodeConfig dto = autoCodeConfigConverter.convertToDto(codeConfigOptional.get());
                AutoCodeConfigMinimal autoCodeConfigMinimal = autoCodeConfigMinimalConverter.convertToEntity(dto);
                task.setAutoCodeConfigMinimal(autoCodeConfigMinimal);
                if (autoCodeConfigMinimal.getGenPolicy() != null && autoCodeConfigMinimal.getGenPolicy() != GenPolicy.None) {
                    task.setGenPolicy(GenPolicy.valueOf(autoCodeConfigMinimal.getGenPolicy().name()));
                    task.setOpenAPISpec(autoCodeConfigMinimal.getOpenAPISpec());
                }
            }
            if (projectSync != null){
                task.setCategories(projectSync.getCategories());
                task.setDeleteAll(projectSync.isDeleteAll());
            }
            Response<Users> usersResponse = usersService.findById(project.getCreatedBy());

            String ownerEmail = usersResponse.getData().getEmail();//projectUsersList.get(0).getUsers().getEmail();
            Optional<UsersPassword> usersPasswordOptional = usersPasswordRepository.findByUsersEmailAndActive(ownerEmail, true);
            if (!usersPasswordOptional.isPresent()) {
                logger.warn("Ignoring Git sync for project with ID [{}] with name [{}] because of no valid owner is active.", project.getId(), project.getName());
                return;
            }

            Response<String> accessKeyResponse = usersService.generate(ownerEmail, project.getOrg().getName());

            String[] accessKey = accessKeyResponse.getData().split(":");

            task.setProjectUser(accessKey[0]);
            task.setProjectGrant(accessKey[1]);
            //TODO

            Optional<SystemSetting> systemSettingOptional = this.systemSettingRepository.findByKey("fx.base.url");
            if (systemSettingOptional.isPresent()) {
                task.setFxUrl(systemSettingOptional.get().getValue());
            } else {
                task.setFxUrl("http://fx-control-plane:8080");
            }

            //Sending event
            String taskId = RandomStringUtils.randomAlphanumeric(24);
            task.setTaskId(taskId);
            try {
                projectSyncEvent(project, Status.In_progress, Entity.Project, task.getTaskId());
            } catch (Exception ex) {
                logger.warn("Exception sending project sync event");
            }
            amqpClientService.sendTask(task, gaaSQueue);
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }


    public void processAutoCodeconfig(Project project, AutoCodeConfig codeConfig, List<TestSuiteAddToVCRequest> testSuiteAddToVCRequests) {
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
                if (StringUtils.isNotEmpty(account.getSecretKey())) {
                    task.setVcPassword(encryptor.decrypt(account.getSecretKey()));
                }
            }
            task.setVcLastCommit(project.getLastCommit());
            if (codeConfig != null){
                AutoCodeConfigMinimal autoCodeConfigMinimal = autoCodeConfigMinimalConverter.convertToEntity(codeConfig);
                task.setAutoCodeConfigMinimal(autoCodeConfigMinimal);
                if (autoCodeConfigMinimal.getGenPolicy() != null && autoCodeConfigMinimal.getGenPolicy() != GenPolicy.None) {
                    task.setGenPolicy(GenPolicy.valueOf(autoCodeConfigMinimal.getGenPolicy().name()));
                    task.setOpenAPISpec(autoCodeConfigMinimal.getOpenAPISpec());
                }
            }


            if (!CollectionUtils.isEmpty(testSuiteAddToVCRequests)) {
                task.setTestSuiteAddToVCRequests(testSuiteAddToVCRequests);
            }


            Response<Users> usersResponse = usersService.findById(project.getCreatedBy());

            String ownerEmail = usersResponse.getData().getEmail();//projectUsersList.get(0).getUsers().getEmail();
            Optional<UsersPassword> usersPasswordOptional = usersPasswordRepository.findByUsersEmailAndActive(ownerEmail, true);
            if (!usersPasswordOptional.isPresent()) {
                logger.warn("Ignoring Git sync for project with ID [{}] with name [{}] because of no valid owner is active.", project.getId(), project.getName());
                return;
            }

            Response<String> accessKeyResponse = usersService.generate(ownerEmail, project.getOrg().getName());

            String[] accessKey = accessKeyResponse.getData().split(":");

            task.setProjectUser(accessKey[0]);
            task.setProjectGrant(accessKey[1]);
            //TODO

            Optional<SystemSetting> systemSettingOptional = this.systemSettingRepository.findByKey("fx.base.url");
            if (systemSettingOptional.isPresent()) {
                task.setFxUrl(systemSettingOptional.get().getValue());
            } else {
                task.setFxUrl("http://fx-control-plane:8080");
            }

            //Sending event
            String taskId = RandomStringUtils.randomAlphanumeric(24);
            task.setTaskId(taskId);
            try {
                projectSyncEvent(project, Status.In_progress, Entity.Project, task.getTaskId());
            } catch (Exception ex) {
                logger.warn("Exception sending project sync event");
            }


            amqpClientService.sendTask(task, gaaSQueue);
        } catch (Exception ex) {
            logger.warn(ex.getLocalizedMessage(), ex);
        }
    }


    public void projectSyncEvent(Project project, Status status, Entity entityType, String taskId) {

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


        logger.info("Sending event for publish on project [{}] and status [{}] for task type [{}]" , project.getId(), status.toString(), event.getName());
        localEventPublisher.publish(event);
    }

}
