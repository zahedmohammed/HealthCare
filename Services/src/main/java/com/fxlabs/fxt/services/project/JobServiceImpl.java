package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.JobConverter;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import com.fxlabs.fxt.services.exceptions.FxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class JobServiceImpl extends GenericServiceImpl<Job, com.fxlabs.fxt.dto.project.Job, String> implements JobService {

    private JobRepository jobRepository;
    private ProjectService projectService;

    @Autowired
    public JobServiceImpl(JobRepository repository, JobConverter converter, ProjectService projectService) {
        super(repository, converter);
        this.jobRepository = repository;
        this.projectService = projectService;
    }

    /*@Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> save(List<com.fxlabs.fxt.dto.project.Job> jobs, String user) {
        if (CollectionUtils.isEmpty(jobs)) {
            throw new FxException("Invalid arguments");
        }
        final List<Message> messages = new ArrayList<>();
        Response response = new Response<>();
        jobs.stream().forEach(job -> {
            Response<com.fxlabs.fxt.dto.project.Job> jobResponse = save(job, user);
            if (!CollectionUtils.isEmpty(jobResponse.getMessages())) {
                messages.addAll(jobResponse.getMessages());
            }
            if (jobResponse.isErrors()) {
                response.withErrors(true);
            }
        });

        return response.withMessages(messages);
    }*/

    @Override
    public Response<com.fxlabs.fxt.dto.project.Job> save(com.fxlabs.fxt.dto.project.Job job, String user) {
        Date next = CronUtils.cronNext(job.getCron());
        job.setNextFire(next);
        return super.save(job, user);
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> findByProjectId(String projectId, String user, Pageable pageable) {
        // check user has access to project
        projectService.isUserEntitled(projectId, user);
        List<Job> jobs = this.jobRepository.findByProjectId(projectId, pageable);
        return new Response<>(converter.convertToDtos(jobs));
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> deleteByProjectId(String projectId, String user, Pageable pageable) {
        // check user has access to project
        Response<List<com.fxlabs.fxt.dto.project.Job>> jobs = findByProjectId(projectId, user, pageable);
        jobs.getData().stream().forEach(job -> {
            job.setDeleted(true);
        });
        return save(jobs.getData(), user);
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> findAll(String user, Pageable pageable) {
        // check user has access to project
        // find owned projects org --> projects --> jobs
        // users --> org or users --> projects
        // least - a project should be visible to owner
        Response<List<Project>> projectsResponse = projectService.findProjects(user, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        final List<Job> jobs = new ArrayList<>();

        projectsResponse.getData().stream().forEach(p -> {
            List<Job> _jobs = jobRepository.findByProjectId(p.getId(), PageRequest.of(0, 20));
            if (!CollectionUtils.isEmpty(_jobs)) {
                jobs.addAll(_jobs);
            }
        });

        return new Response<>(converter.convertToDtos(jobs));

    }

    @Override
    public void isUserEntitled(String jobId, String user) {
        // TODO - user has access to job/project
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (!jobOptional.isPresent()) {
            throw new FxException(String.format("Invalid job id [%s]", jobId));
        }
        projectService.isUserEntitled(jobOptional.get().getProject().getId(), user);
    }

}
