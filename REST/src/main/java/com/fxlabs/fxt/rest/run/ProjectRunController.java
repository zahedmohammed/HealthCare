package com.fxlabs.fxt.rest.run;

import com.fxlabs.fxt.dto.run.DataSet;
import com.fxlabs.fxt.dto.run.Run;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.run.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.PROJECT_RUNS_BASE;

@RestController
@RequestMapping(PROJECT_RUNS_BASE)
public class ProjectRunController extends BaseController<Run, String> {

    private RunService runService;

    @Autowired
    public ProjectRunController(
            RunService service) {
        super(service);
        this.runService = service;
    }

    @RequestMapping(value = "/{id}/test-suites", method = RequestMethod.GET)
    public Response<List<DataSet>> run(@PathVariable("id") String id,
                                       @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                       @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize
                                       ) {
        return runService.findByRunId(id, new PageRequest(page, pageSize, SORT_BY_CREATE_DT));
    }


}
