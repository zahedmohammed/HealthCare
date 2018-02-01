package com.fxlabs.fxt.services.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.services.base.GenericService;

public interface ProjectService extends GenericService<Project, String> {

    Response<Project> findByName(String name, String owner);

}
