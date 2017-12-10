package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
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

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public Response<Project> findById(@PathVariable("name") String name) {
        return ((ProjectService) service).findByName(name, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }


}
