package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Environment;
import com.fxlabs.fxt.services.base.GenericService;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface EnvironmentService extends GenericService<Environment, String> {
    public Response<List<Environment>> findByProjectId(String projectId, String user);
}
