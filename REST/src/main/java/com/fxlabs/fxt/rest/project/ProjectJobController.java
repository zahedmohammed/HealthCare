package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.ProjectJob;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.project.ProjectJobService;
import com.fxlabs.fxt.services.run.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_JOBS_BASE;

@RestController
@RequestMapping(PROJECT_JOBS_BASE)
public class ProjectJobController extends BaseController<ProjectJob, String> {

    RunService runService;

    @Autowired
    public ProjectJobController(
            ProjectJobService service, RunService runService) {
        super(service);
        this.runService = runService;
    }


    @RequestMapping(value = "/{id}/run", method = RequestMethod.POST)
    public Response<Run> run(@PathVariable("id") String id) {
        return runService.run(id);
    }


    @RequestMapping(value = "/instance/{id}", method = RequestMethod.GET)
    public Response<Run> instance(@PathVariable("id") String id) {
        return runService.findById(id);
    }


}
