package com.fxlabs.fxt.rest.project;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.project.TestSuite;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.project.TestSuiteService;
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
@RequestMapping(TEST_SUITES_BASE)
public class TestSuiteController {

    private TestSuiteService service;

    @Autowired
    public TestSuiteController(TestSuiteService service) {
        this.service = service;
    }


    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<TestSuite>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                             @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findAll(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public Response<List<TestSuite>> findAll(@PathVariable("keyword") String keyword,
                                             @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                             @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.search(keyword, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }


    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<TestSuite> findById(@PathVariable("id") String id) {
        return service.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<TestSuite>> create(@Valid @RequestBody List<TestSuite> dtos) {
        return service.save(dtos, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<TestSuite> create(@Valid @RequestBody TestSuite dto) {
        return service.save(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<TestSuite> update(@Valid @RequestBody TestSuite dto) {
        return service.save(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<TestSuite> delete(@PathVariable("id") String id) {
        return service.delete(id, SecurityUtil.getCurrentAuditor());
    }


}
