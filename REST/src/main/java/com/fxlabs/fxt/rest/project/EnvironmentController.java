package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Environment;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.EnvironmentService;
import com.fxlabs.fxt.services.project.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.ENVS_BASE;
import static com.fxlabs.fxt.rest.base.BaseController.JOBS_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(ENVS_BASE)
public class EnvironmentController extends BaseController<Environment, String> {

    @Autowired
    public EnvironmentController(
            EnvironmentService service) {
        super(service);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/project-id/{id}", method = RequestMethod.GET)
    public Response<List<Environment>> findByProjectId(@PathVariable("id") String projectId) {
        return ((EnvironmentService) service).findByProjectId(projectId, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

}
