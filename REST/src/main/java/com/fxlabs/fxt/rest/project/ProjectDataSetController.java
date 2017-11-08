package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.dto.project.ProjectDataSet;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.base.Response;
import com.fxlabs.fxt.services.project.ProjectDataSetService;
import com.fxlabs.fxt.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_DATASETS_BASE;

@RestController
@RequestMapping(PROJECT_DATASETS_BASE)
public class ProjectDataSetController extends BaseController {

    @Autowired
    public ProjectDataSetController(
            ProjectDataSetService service) {
        super(service);
    }


}
