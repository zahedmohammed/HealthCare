package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.converters.project.JobConverter;
import com.fxlabs.fxt.dao.entity.project.Job;
import com.fxlabs.fxt.dao.repository.jpa.JobRepository;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class JobServiceImpl extends GenericServiceImpl<Job, com.fxlabs.fxt.dto.project.Job, String> implements JobService {

    private JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository repository, JobConverter converter) {
        super(repository, converter);
        this.jobRepository = repository;
    }

    @Override
    public Response<List<com.fxlabs.fxt.dto.project.Job>> findByProjectId(String projectId, String user) {
        List<Job> jobs = this.jobRepository.findByProjectId(projectId);
        return new Response<>(converter.convertToDtos(jobs));
    }

}
