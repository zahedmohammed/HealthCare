package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.EnvironmentConverter;
import com.fxlabs.fxt.dao.entity.project.Environment;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.jpa.EnvironmentRepository;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dto.base.Message;
import com.fxlabs.fxt.dto.base.MessageType;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Account;
import com.fxlabs.fxt.dto.project.AutoCodeConfig;
import com.fxlabs.fxt.dto.project.GenPolicy;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    public EnvironmentServiceImpl(EnvironmentRepository repository, JobRepository jobRepository, EnvironmentConverter converter, ProjectService projectService) {
        super(repository, converter);
        this.environmentRepository = repository;
        this.projectService = projectService;
        this.jobRepository = jobRepository;
    }


    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Environment>> findByProjectId(String projectId, String user) {
        List<Environment> environments = environmentRepository.findByProjectId(projectId);
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
    public void isUserEntitled(String id, String user) {
        Optional<Environment> environmentOptional = environmentRepository.findById(id);
        if (!environmentOptional.isPresent()) {
            throw new FxException(String.format("User [%s] not entitled to the resource [%s].", user, id));
        }
        projectService.isUserEntitled(environmentOptional.get().getProjectId(), user);
    }

    @Override
    public Response<com.fxlabs.fxt.dto.project.Environment> create(com.fxlabs.fxt.dto.project.Environment environment, String projectId, String owenr, String orgId){

        if (environment == null || StringUtils.isEmpty(projectId)) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid request for  Project Env configuaration"));
        }

        if (environment.getName() == null && environment.getBaseUrl() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Name is empty."));
        }

        if (environment.getBaseUrl() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Url is empty."));
        }

        Response<Project> optionalProject = projectService.findById(projectId, owenr);
        if (!optionalProject.isErrors() || optionalProject.getData() == null) {
            return new Response<>().withErrors(true).withMessage(new Message(MessageType.ERROR, null, "Invalid access"));
        }
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

        jobRepository.save(job);

        return environmentResponse;
    }


}
