package com.fxlabs.fxt.rest.run;

import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.run.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_RUNS_BASE;

@RestController
@RequestMapping(PROJECT_RUNS_BASE)
public class ProjectRunController extends BaseController<Run> {

    RunService runService;

    @Autowired
    public ProjectRunController(
            RunService service) {
        super(service);
        this.runService = service;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Response<Run> create(@PathVariable("id") String id) {
        return runService.run(id);
    }


}
