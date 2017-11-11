package com.fxlabs.fxt.rest.base;

import com.fxlabs.fxt.dto.project.Project;
import com.fxlabs.fxt.services.base.GenericService;
import com.fxlabs.fxt.services.base.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<D> {

    public final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String API_BASE = "/api/v1";
    public static final String USER_BASE = API_BASE + "/users";

    public static final String PROJECTS_BASE = API_BASE + "/projects";
    public static final String PROJECT_DATASETS_BASE = API_BASE + "/project-datasets";
    public static final String PROJECT_ENVIRONMENTS_BASE = API_BASE + "/project-environments";
    public static final String PROJECT_JOBS_BASE = API_BASE + "/project-jobs";
    public static final String PROJECT_RUNS_BASE = API_BASE + "/project-runs";

    public final String PAGE_PARAM = "page";
    public final String PAGE_SIZE_PARAM = "pageSize";

    public final String DEFAULT_PAGE_VALUE = "0";
    public final String DEFAULT_PAGE_SIZE_VALUE = "20";

    private GenericService<D> service;

    protected BaseController(GenericService<D> service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response<List<D>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findAll(null, new PageRequest(0, 20));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<D> findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<D> create(D dto) {
        return service.save(dto);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<D> update(D dto) {
        return service.save(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<D> delete(@PathVariable("id") String id) {
        return service.delete(id);
    }


}
