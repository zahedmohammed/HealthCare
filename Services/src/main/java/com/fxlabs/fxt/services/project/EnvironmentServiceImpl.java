package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.EnvironmentConverter;
import com.fxlabs.fxt.converters.project.JobConverter;
import com.fxlabs.fxt.converters.project.ProjectConverter;
import com.fxlabs.fxt.dao.entity.project.Auth;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.entity.project.JobNotification;
import com.fxlabs.fxt.dao.repository.jpa.EnvironmentRepository;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.JobIssueTracker;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class EnvironmentServiceImpl extends GenericServiceImpl<Environment, com.fxlabs.fxt.dto.project.Environment, String> implements EnvironmentService {

    private EnvironmentRepository environmentRepository;
    private ProjectService projectService;
    private JobRepository jobRepository;
    private ProjectConverter projectConverter;

    @Autowired
    public EnvironmentServiceImpl(EnvironmentRepository repository, JobRepository jobRepository, ProjectConverter projectConverter,
                                  EnvironmentConverter converter, ProjectService projectService) {
        super(repository, converter);
        this.environmentRepository = repository;
        this.projectService = projectService;
        this.jobRepository = jobRepository;
        this.projectConverter = projectConverter;
    }


    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Environment>> findByProjectId(String projectId, String orgId) {
        Response<Project> optionalProject = projectService.findById(projectId, orgId);
        if (optionalProject.isErrors() || optionalProject.getData() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }

        List<Environment> environments = environmentRepository.findByProjectId(projectId);

        if (environments == null || CollectionUtils.isEmpty(environments)) {
            environments = new ArrayList<>();
//            Environment env = new Environment();
//            env.setAuths(Arrays.asList(new Auth()));
//            environments.add(env);
        }
        return new Response<>(converter.convertToDtos(environments));
    }

    @Override
    public Response<Long> count(String org, Pageable pageable) {

        Response<List<Project>> projectsResponse = projectService.findProjects(org, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        AtomicLong al = new AtomicLong(0);

        projectsResponse.getData().stream().forEach(p -> {
            Long count = environmentRepository.countByProjectIdAndInactive(p.getId(), false);
            if (count != null) {
                al.getAndAdd(count);
            }

        });

        return new Response<>(al.get());

    }


    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Environment>> save(List<com.fxlabs.fxt.dto.project.Environment> envs, String projectId, String org) {
        for (com.fxlabs.fxt.dto.project.Environment env : envs) {
            if (StringUtils.isEmpty(env.getId())) {
                create(env, projectId, org);
            } else {
                update(env, projectId, org);
            }
        }
        return findByProjectId(projectId, org);
    }

    @Override
    public void isUserEntitled(String id, String user) {
        /*Optional<Environment> environmentOptional = environmentRepository.findById(id);
        if (!environmentOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }
        projectService.isUserEntitled(environmentOptional.get().getProjectId(), user);*/
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.Environment> findById(String id, String org) {
        Optional<Environment> optionalEnvironment = ((EnvironmentRepository) repository).findById(id);

        if (!optionalEnvironment.isPresent()) {
            throw new FxException("Invalid request for Environment");
        }
        com.fxlabs.fxt.dto.project.Environment dto = converter.convertToDto(optionalEnvironment.get());
       // testSuiteConverter.copyArraysToText(dto);

        return new Response<>(dto);
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.Environment> create(com.fxlabs.fxt.dto.project.Environment environment, String projectId, String orgId) {

        if (environment == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project environment"));
        }

        if (StringUtils.isEmpty(environment.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Name is empty."));
        }

        if (StringUtils.isEmpty(environment.getBaseUrl())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Url is empty."));
        }

        if (StringUtils.isEmpty(projectId)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project."));
        }

        Response<Project> optionalProject = projectService.findById(projectId, orgId);
        if (optionalProject.isErrors() || optionalProject.getData() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        environment.setProjectId(optionalProject.getData().getId());
        //        - Name: Dev                [Default value]
        //        - Environment:             [Autocomplete options]
        //        - Region: FXLabs/US_WEST_1 [Autocomplete options]
        //        - Cron:  0 15 10 ? * *     [Autocomplete options]
        Response<com.fxlabs.fxt.dto.project.Environment> environmentResponse = save(environment);

        Job job = new Job();
        job.setName(environment.getName());
        job.setEnvironment(converter.convertToEntity(environmentResponse.getData()));
        job.setCron("0 15 10 ? * *");
        job.setRegions("FXLabs/US_WEST_1");
        job.setInactive(false);

        //Setting default issuetracker
        com.fxlabs.fxt.dao.entity.project.JobIssueTracker jobIssueTracker = new com.fxlabs.fxt.dao.entity.project.JobIssueTracker();
        jobIssueTracker.setUrl("<<changeme>>");
        jobIssueTracker.setAccount("Default_GitHub");
        jobIssueTracker.setName("FXLabs/Dev-IssueTracker");
        job.setIssueTracker(jobIssueTracker);

        //Setting default notification

        List<JobNotification> listNotification = new ArrayList<>();
        JobNotification jnEmail = new JobNotification();
        jnEmail.setName("FXLabs/Dev-Email-Notification");
        jnEmail.setTo("<<changeme>>");
        jnEmail.setAccount("Default_Email");
        listNotification.add(jnEmail);


        JobNotification jn = new JobNotification();
        jn.setAccount("FXLabs/Dev-Slack-Notification");
        jn.setName("Default_Slack");
        jn.setChannel("<<changeme>>");
        listNotification.add(jn);
        job.setNotifications(listNotification);

        job.setProject(projectConverter.convertToEntity(optionalProject.getData()));

        jobRepository.save(job);

        return environmentResponse;
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.Environment> update(com.fxlabs.fxt.dto.project.Environment environment, String projectId, String orgId) {

        if (environment == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid project environment"));
        }

        if (StringUtils.isEmpty(environment.getName())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Name is empty."));
        }

        if (StringUtils.isEmpty(environment.getBaseUrl())) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Url is empty."));
        }

        Response<Project> optionalProject = projectService.findById(projectId, orgId);
        if (optionalProject.isErrors() || optionalProject.getData() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
        environment.setProjectId(optionalProject.getData().getId());

        Response<com.fxlabs.fxt.dto.project.Environment> environmentResponse = save(environment);

        return environmentResponse;
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.Environment> delete(String projectId, String envId, String orgId) {

        Response<List<com.fxlabs.fxt.dto.project.Environment>> environments = findByProjectId(projectId, orgId);

        environments.getData().forEach(env -> {
            if (StringUtils.equals(env.getId(), envId)) {
                delete(envId, orgId);
            }
        });

        return new Response<>();
    }


}
