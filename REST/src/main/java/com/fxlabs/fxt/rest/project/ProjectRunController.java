package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.project.ProjectRunService;
import com.fxlabs.fxt.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_RUNS_BASE;

@RestController
@RequestMapping(PROJECT_RUNS_BASE)
public class ProjectRunController extends BaseController {

    @Autowired
    public ProjectRunController(
            ProjectRunService service) {
        super(service);
    }


}
