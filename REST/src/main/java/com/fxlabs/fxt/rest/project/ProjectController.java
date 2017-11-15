package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECTS_BASE;

@RestController
@RequestMapping(PROJECTS_BASE)
public class ProjectController extends BaseController<Project, String> {

    @Autowired
    public ProjectController(
            ProjectService projectService) {
        super(projectService);
    }


}
