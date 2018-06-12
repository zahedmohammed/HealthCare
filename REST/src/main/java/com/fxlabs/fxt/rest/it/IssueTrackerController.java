package com.fxlabs.fxt.rest.it;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.it.IssueTracker;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.it.IssueTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(ISSUE_TRACKER_BASE)
@Validated
public class IssueTrackerController {

    private IssueTrackerService service;

    @Autowired
    public IssueTrackerController(IssueTrackerService service) {
        this.service = service;
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<IssueTracker>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                                @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Max(20) Integer pageSize) {

        return service.findAll(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/skill-type/{type}", method = RequestMethod.GET)
    public Response<List<IssueTracker>> findByType(@PathVariable("type") String type,
                                                   @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                   @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {

        return service.findBySkillType(type, SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<IssueTracker> findById(@PathVariable("id") String id) {

        return service.findById(id, SecurityUtil.getOrgId());
    }

    /*@Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<SkillSubscription> add(@RequestBody SkillSubscription request) {
        return service.save(request, SecurityUtil.getCurrentAuditor());
    }*/

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/issue-tracker-bot", method = RequestMethod.POST)
    public Response<IssueTracker> addIssueTrackerBot(@RequestBody IssueTracker request) {

        return service.addITBot(request, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/issue-tracker-bot/{id}", method = RequestMethod.DELETE)
    public Response<IssueTracker> deleteIssueTrackerBot(@PathVariable("id") String id) {

        return service.deleteITBot(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }
//
//    @Secured(ROLE_USER)
//    @RequestMapping(value = "/exec-bot", method = RequestMethod.POST)
//    public Response<SkillSubscription> addExecBot(@RequestBody SkillSubscription request) {
//        return service.addExecBot(request, SecurityUtil.getCurrentAuditor());
//    }

//    @Secured(ROLE_USER)
//    @RequestMapping(value = "/exec-bot/{id}", method = RequestMethod.DELETE)
//    public Response<SkillSubscription> deleteExecBot(@PathVariable("id") String id) {
//        return service.deleteExecBot(id, SecurityUtil.getCurrentAuditor());
//    }


    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Response<IssueTracker> update(@RequestBody IssueTracker dto) {

        return service.save(dto, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    /*@Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<SkillSubscription> delete(@PathVariable("id") String id) {
        return service.delete(id, SecurityUtil.getCurrentAuditor());
    }*/


}
