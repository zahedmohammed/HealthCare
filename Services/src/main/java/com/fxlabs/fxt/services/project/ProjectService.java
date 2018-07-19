package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectImports;
import com.fxlabs.fxt.dto.project.ProjectSaving;
import com.fxlabs.fxt.dto.project.ProjectSync;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface ProjectService extends GenericService<Project, String> {

    Response<List<Project>> findProjects(String owner, Pageable pageable);

    Response<Long> countProjects(String org);

    Response<Project> findByName(String name, String owner);

    Response<Project> findByOrgAndName(String name, String owner);

    Response<Project> findGitByProjectId(String projectId, String user);

    Response<Project> saveProject(Project request, String org, String user);

    Response<Project> save(Project dto, String user);

    Response<Project> delete(String id, String user);

    Response<Boolean> saveProjectImports(ProjectImports projectImports, String user);

    Response<Boolean> saveProjectSync(ProjectSync projectSync, String user);

    Response<Project> save(Project dto, String org, String user);

    Response<Project> delete(String id, String org, String user);

    Response<Project> add(Project request, String org, String owner);

    Response<ProjectSaving> getProjectSavings(String id, String org, String owner);


}
