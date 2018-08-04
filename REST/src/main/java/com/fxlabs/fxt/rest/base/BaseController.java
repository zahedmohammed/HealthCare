package com.fxlabs.fxt.rest.base;

import com.fxlabs.fxt.services.base.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * @author Intesar Shannan Mohammed
 */
public abstract class BaseController<D, ID extends Serializable> {

    public static final String API_BASE = "/api/v1";
    public static final String USER_BASE = API_BASE + "/users";
    public static final String ORG_BASE = API_BASE + "/orgs";
    public static final String PROJECTS_BASE = API_BASE + "/projects";
    public static final String JOBS_BASE = API_BASE + "/jobs";
    public static final String ENVS_BASE = API_BASE + "/envs";
    public static final String TEST_SUITES_BASE = API_BASE + "/test-suites";
    public static final String DATA_RECORD_BASE = API_BASE + "/data-records";
    public static final String DATA_SET_BASE = API_BASE + "/data-sets";
    public static final String PROJECT_RUNS_BASE = API_BASE + "/runs";
    public static final String SYSTEM_SETTING_BASE = API_BASE + "/system-settings";
    public static final String ALERT_BASE = API_BASE + "/alerts";
    public static final String CLUSTER_BASE = API_BASE + "/bot-clusters";
    public static final String ACCOUNT_BASE = API_BASE + "/accounts";
    public static final String NOTIFICATION_BASE = API_BASE + "/notifications";
    public static final String SKILLS_BASE = API_BASE + "/skills";
    public static final String ISSUE_TRACKER_BASE = API_BASE + "/issue-trackers";
    public static final String VAULT_BASE = API_BASE + "/vault";
    public static final String DASHBOARD_BASE = API_BASE + "/dashboard";
    public static final String EVENTS_BASE = API_BASE + "/events";


    public static final Sort SORT_BY_CREATE_DT = new Sort(Sort.Direction.DESC, "createdDate");

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_PROJECT_MANAGER = "ROLE_PROJECT_MANAGER";
    public static final String ROLE_ENTERPRISE_ADMIN = "ROLE_ENTERPRISE_ADMIN";

    public final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String PAGE_PARAM = "page";
    public static final String PAGE_SIZE_PARAM = "pageSize";
    public static final String DEFAULT_PAGE_VALUE = "0";
    public static final String DEFAULT_PAGE_SIZE_VALUE = "20";
    public static final String DEFAULT_MAX_PAGE_SIZE_VALUE = "100";
    public static final String DEFAULT_1k_PAGE_SIZE_VALUE = "1000";

    public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC, "modifiedDate", "createdDate");

    protected GenericService<D, ID> service;

    /*protected BaseController(GenericService<D, ID> service) {
        this.service = service;
    }*/

    /*@CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<D>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findAll(SecurityUtil.getCurrentAuditor(), new PageRequest(0, 20));
    }


    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<D> findById(@PathVariable("id") ID id) {
        return service.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<D>> create(@Valid @RequestBody List<D> dtos) {
        return service.save(dtos, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<D> create(@Valid @RequestBody D dto) {
        return service.save(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<D> update(@Valid @RequestBody D dto) {
        return service.save(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<D> delete(@PathVariable("id") ID id) {
        return service.delete(id, SecurityUtil.getCurrentAuditor());
    }*/


}
