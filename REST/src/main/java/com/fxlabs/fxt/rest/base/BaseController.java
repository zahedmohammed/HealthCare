package com.fxlabs.fxt.rest.base;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.services.base.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public abstract class BaseController<D, ID extends Serializable> {

    public static final String API_BASE = "/api/v1";
    public static final String USER_BASE = API_BASE + "/users";
    public static final String PROJECTS_BASE = API_BASE + "/projects";
    public static final String JOBS_BASE = API_BASE + "/jobs";
    public static final String PROJECT_DATASETS_BASE = API_BASE + "/test-suites";
    public static final String PROJECT_RUNS_BASE = API_BASE + "/runs";
    public static final Sort SORT_BY_CREATE_DT = new Sort(Sort.Direction.DESC, "createdDate");
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_AdMIN = "ROLE_ADMIN";
    public final Logger logger = LoggerFactory.getLogger(getClass());
    public final String PAGE_PARAM = "page";
    public final String PAGE_SIZE_PARAM = "pageSize";
    public final String DEFAULT_PAGE_VALUE = "0";
    public final String DEFAULT_PAGE_SIZE_VALUE = "20";
    protected GenericService<D, ID> service;

    protected BaseController(GenericService<D, ID> service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response<List<D>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findAll(null, new PageRequest(0, 20));
    }


    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<D> findById(@PathVariable("id") ID id) {
        return service.findById(id);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<D>> create(@Valid @RequestBody List<D> dtos) {
        return service.save(dtos);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<D> create(@Valid @RequestBody D dto) {
        return service.save(dto);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<D> update(@Valid @RequestBody D dto) {
        return service.save(dto);
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<D> delete(@PathVariable("id") ID id) {
        return service.delete(id);
    }


}
