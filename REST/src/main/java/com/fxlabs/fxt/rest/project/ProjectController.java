package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.project.ProjectService;
import com.fxlabs.fxt.services.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECTS_BASE;

@RestController
@RequestMapping(PROJECTS_BASE)
public class ProjectController extends BaseController<Project> {

    @Autowired
    public ProjectController(
            ProjectService projectService) {
        super(projectService);
    }


}
