package com.fxlabs.fxt.rest.skills;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.skills.SkillSubscription;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.skills.SkillSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(SUBSCRIPTION_BASE)
public class SkillSubscriptionsController {

    private SkillSubscriptionService service;

    @Autowired
    public SkillSubscriptionsController(SkillSubscriptionService service) {
        this.service = service;
    }

    @Secured(ROLE_USER)
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<SkillSubscription>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                     @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findAll(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/skill-type/{type}", method = RequestMethod.GET)
    public Response<List<SkillSubscription>> findByType(@PathVariable("type") String type,
                                                        @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                        @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findBySkillType(type, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<SkillSubscription> findById(@PathVariable("id") String id) {
        return service.findById(id, SecurityUtil.getCurrentAuditor());
    }

    /*@Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<SkillSubscription> add(@RequestBody SkillSubscription request) {
        return service.save(request, SecurityUtil.getCurrentAuditor());
    }*/

    @Secured(ROLE_USER)
    @RequestMapping(value = "/issue-tracker-bot", method = RequestMethod.POST)
    public Response<SkillSubscription> addIssueTrackerBot(@RequestBody SkillSubscription request) {
        return service.addITBot(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/issue-tracker-bot/{id}", method = RequestMethod.DELETE)
    public Response<SkillSubscription> deleteIssueTrackerBot(@PathVariable("id") String id) {
        return service.deleteITBot(id, SecurityUtil.getCurrentAuditor());
    }
//
//    @Secured(ROLE_USER)
//    @RequestMapping(value = "/exec-bot", method = RequestMethod.POST)
//    public Response<SkillSubscription> addExecBot(@RequestBody SkillSubscription request) {
//        return service.addExecBot(request, SecurityUtil.getCurrentAuditor());
//    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/exec-bot/{id}", method = RequestMethod.DELETE)
    public Response<SkillSubscription> deleteExecBot(@PathVariable("id") String id) {
        return service.deleteExecBot(id, SecurityUtil.getCurrentAuditor());
    }


    @Secured(ROLE_USER)
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Response<SkillSubscription> update(@RequestBody SkillSubscription dto) {
        return service.save(dto, SecurityUtil.getCurrentAuditor());
    }

    /*@Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<SkillSubscription> delete(@PathVariable("id") String id) {
        return service.delete(id, SecurityUtil.getCurrentAuditor());
    }*/


}
