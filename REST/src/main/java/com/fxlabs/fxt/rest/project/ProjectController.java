package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECTS_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(PROJECTS_BASE)
public class ProjectController extends BaseController<Project, String> {

    @Autowired
    public ProjectController(
            ProjectService projectService) {
        super(projectService);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public Response<Project> findById(@PathVariable("name") String name) {
        return ((ProjectService) service).findByName(name, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }


}
