package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.ProjectJob;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.ProjectJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_JOBS_BASE;

@RestController
@RequestMapping(PROJECT_JOBS_BASE)
public class ProjectJobController extends BaseController<ProjectJob, String> {

    @Autowired
    public ProjectJobController(
            ProjectJobService service) {
        super(service);
    }


}
