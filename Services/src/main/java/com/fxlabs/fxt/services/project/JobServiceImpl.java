package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.JobConverter;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> findByProjectId(String projectId, String user) {
        // TODO - check user has access to project
        List<Job> jobs = this.jobRepository.findByProjectId(projectId);
        return new Response<>(converter.convertToDtos(jobs));
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> findAll(String user, Pageable pageable) {
        // TODO - check user has access to project
        // find owned projects org --> projects --> jobs
        // users --> org or users --> projects
        // least - a project should be visible to owner
        Response<List<Project>> projectsResponse = projectService.findProjects(user, pageable);
        if (projectsResponse.isErrors() || CollectionUtils.isEmpty(projectsResponse.getData())) {
            return new Response<>().withMessages(projectsResponse.getMessages()).withErrors(true);
        }

        final List<Job> jobs = new ArrayList<>();

        projectsResponse.getData().stream().forEach(p -> {
            List<Job> _jobs = jobRepository.findByProjectId(p.getId());
            if (!CollectionUtils.isEmpty(_jobs)) {
                jobs.addAll(_jobs);
            }
        });

        return new Response<>(converter.convertToDtos(jobs));

    }

}
