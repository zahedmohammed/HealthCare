package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fxlabs.fxt.rest.base.BaseController.JOBS_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(JOBS_BASE)
public class JobController extends BaseController<Job, String> {

    @Autowired
    public JobController(
            JobService jobService) {
        super(jobService);
    }

}
