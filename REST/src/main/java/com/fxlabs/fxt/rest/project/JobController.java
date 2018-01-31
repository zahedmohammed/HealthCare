package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.fxlabs.fxt.rest.base.BaseController.JOBS_BASE;
import static com.fxlabs.fxt.rest.base.BaseController.PROJECTS_BASE;

@RestController
@RequestMapping(JOBS_BASE)
public class JobController extends BaseController<Job, String> {

    @Autowired
    public JobController(
            JobService jobService) {
        super(jobService);
    }

}
