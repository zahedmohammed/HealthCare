package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.DataRecord;
import com.fxlabs.fxt.dto.project.DataSet;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.project.DataRecordService;
import com.fxlabs.fxt.services.project.TestSuiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Mohammed Shoukath Ali
 */
@RestController
@RequestMapping(DATA_RECORD_BASE)
public class DataRecordController {

    private DataRecordService service;

    @Autowired
    public DataRecordController(DataRecordService service) {
        this.service = service;
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<DataRecord>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findAll(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<DataRecord>> create(@Valid @RequestBody List<DataRecord> dtos) {
        return service.save(dtos, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<DataRecord> create(@Valid @RequestBody DataRecord dto) {
        return service.save(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<DataRecord> update(@Valid @RequestBody DataRecord dto) {
        return service.save(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<DataRecord> delete(@PathVariable("id") String id) {
        return service.delete(id, SecurityUtil.getCurrentAuditor());
    }


}
