package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Environment;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface EnvironmentService extends GenericService<Environment, String> {
    public Response<List<Environment>> findByProjectId(String projectId, String user);
    Response<Long> count(String user, Pageable pageable);

    public Response<Environment> create(Environment environment,String projectId, String owenr, String orgId);
}
