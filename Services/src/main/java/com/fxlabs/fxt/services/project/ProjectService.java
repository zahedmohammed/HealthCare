package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectGitAccount;
import com.fxlabs.fxt.dto.project.ProjectRequest;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ProjectService extends GenericService<Project, String> {

    Response<List<Project>> findProjects(String owner, Pageable pageable);

    Response<Project> findByName(String name, String owner);

    Response<Project> findProjectById(String id, String owner);

    Response<Project> add(ProjectRequest account, String owner);

}
