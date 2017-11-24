package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.TestSuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_DATASETS_BASE;

@RestController
@RequestMapping(PROJECT_DATASETS_BASE)
public class ProjectDataSetController extends BaseController<TestSuite, String> {

    @Autowired
    public ProjectDataSetController(
            TestSuiteService service) {
        super(service);
    }


}
