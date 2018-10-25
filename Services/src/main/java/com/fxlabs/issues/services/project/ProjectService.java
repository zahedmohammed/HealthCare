package com.fxlabs.issues.services.project;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.services.base.GenericService;
import com.fxlabs.issues.dto.project.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface ProjectService extends GenericService<Project, String> {

    Response<Project> delete(String id, String user, String org);

    Response<Project> save(Project dto, String user, String org);

    Response<List<Project>> findProjects(String owner, Pageable pageable);

    Response<Project> update(Project request, String user, String org);

}
