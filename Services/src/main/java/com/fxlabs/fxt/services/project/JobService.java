package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.services.base.GenericService;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface JobService extends GenericService<Job, String> {

    Response<List<Job>> findByProjectId(String projectId, String user);
}
