package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Secured(ROLE_USER)
    @RequestMapping(value = "/project-id/{id}", method = RequestMethod.GET)
    public Response<List<Job>> findByProjectId(@PathVariable("id") String projectId) {
        return ((JobService) service).findByProjectId(projectId, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

}
