package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.AutoSuggestion;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface JobService extends GenericService<Job, String> {

    Response<com.fxlabs.fxt.dto.project.Job> save(com.fxlabs.fxt.dto.project.Job job, String user, String org);

    Response<com.fxlabs.fxt.dto.project.Job> update(com.fxlabs.fxt.dto.project.Job job, String user);

    Response<List<com.fxlabs.fxt.dto.project.Job>> save(List<com.fxlabs.fxt.dto.project.Job> jobs, String user);

    Response<List<Job>> findByProjectId(String projectId, String user, Pageable pageable);
    Response<List<Job>> findByProjectId(String projectId, String user);

    Response<List<com.fxlabs.fxt.dto.project.Job>> deleteByProjectId(String projectId, String user, Pageable pageable);

    Response<List<com.fxlabs.fxt.dto.project.Job>> findAll(String user, Pageable pageable);

    Response<Long> count(String user, Pageable pageable);

    Response<List<com.fxlabs.fxt.dto.project.Job>> findJobsByIssueTracker(String issueTracker);

}
