package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Environment;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface EnvironmentService extends GenericService<Environment, String> {

    Response<List<Environment>> findByProjectId(String projectId, String org, PageRequest pageable);

    Response<Long> count(String user, Pageable pageable);

    public Response<Environment> findById(String id, String orgId);

    Response<Environment> create(Environment environment, String projectId, String orgId);

    Response<com.fxlabs.fxt.dto.project.Environment> update(com.fxlabs.fxt.dto.project.Environment environment, String projectId, String orgId);

    Response<List<Environment>> save(List<Environment> envs, String projectId, String org);

    Response<com.fxlabs.fxt.dto.project.Environment> delete(String envId, String userId);
}
