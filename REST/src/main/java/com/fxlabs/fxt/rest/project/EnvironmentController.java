package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.Environment;
import com.fxlabs.fxt.services.project.EnvironmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.ENVS_BASE;
import static com.fxlabs.fxt.rest.base.BaseController.ROLE_USER;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(ENVS_BASE)
public class EnvironmentController {

    private EnvironmentService service;

    @Autowired
    public EnvironmentController(
            EnvironmentService service) {
        this.service = service;
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/project-id/{id}", method = RequestMethod.GET)
    public Response<List<Environment>> findByProjectId(@PathVariable("id") String projectId) {
        return service.findByProjectId(projectId, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Environment> findById(@PathVariable("id") String id) {
        return service.findById(id, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<Environment>> create(@Valid @RequestBody List<Environment> dtos) {
        return service.save(dtos, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Environment> create(@Valid @RequestBody Environment dto) {
        return service.save(dto, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Environment> update(@Valid @RequestBody Environment dto) {
        return service.save(dto, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Environment> delete(@PathVariable("id") String id) {
        return service.delete(id, com.fxlabs.fxt.rest.base.SecurityUtil.getCurrentAuditor());
    }


}
