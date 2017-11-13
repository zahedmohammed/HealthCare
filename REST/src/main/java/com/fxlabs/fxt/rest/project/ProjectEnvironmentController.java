package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.ProjectEnvironment;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.ProjectEnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_ENVIRONMENTS_BASE;

@RestController
@RequestMapping(PROJECT_ENVIRONMENTS_BASE)
public class ProjectEnvironmentController extends BaseController<ProjectEnvironment> {

    @Autowired
    public ProjectEnvironmentController(
            ProjectEnvironmentService service) {
        super(service);
    }


}
