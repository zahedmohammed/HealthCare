package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.project.ProjectEnvironmentService;
import com.fxlabs.fxt.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_ENVIRONMENTS_BASE;

@RestController
@RequestMapping(PROJECT_ENVIRONMENTS_BASE)
public class ProjectEnvironmentController extends BaseController {

    @Autowired
    public ProjectEnvironmentController(
            ProjectEnvironmentService service) {
        super(service);
    }


}
