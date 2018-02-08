package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Job;
import com.fxlabs.fxt.services.project.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(JOBS_BASE)
public class JobController /* extends BaseController<Job, String>*/ {

    private JobService service;

    @Autowired
    public JobController(
            JobService jobService) {
        this.service = jobService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Job>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                       @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findAll(com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor(), new PageRequest(0, 20));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/project-id/{id}", method = RequestMethod.GET)
    public Response<List<Job>> findByProjectId(@PathVariable("id") String projectId) {
        return service.findByProjectId(projectId, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Job> findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<Job>> create(@Valid @RequestBody List<Job> dtos) {
        return service.save(dtos);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Job> create(@Valid @RequestBody Job dto) {
        return service.save(dto);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Job> update(@Valid @RequestBody Job dto) {
        return service.save(dto);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Job> delete(@PathVariable("id") String id) {
        return service.delete(id);
    }


}
