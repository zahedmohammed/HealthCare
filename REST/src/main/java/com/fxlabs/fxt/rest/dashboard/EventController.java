package com.fxlabs.fxt.rest.dashboard;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.events.Event;
import com.fxlabs.fxt.dto.it.IssueTrackerSaving;
import com.fxlabs.fxt.dto.project.ProjectSaving;
import com.fxlabs.fxt.dto.run.RunSavings;
import com.fxlabs.fxt.dto.users.Saving;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.clusters.ClusterService;
import com.fxlabs.fxt.services.events.RemoteEventService;
import com.fxlabs.fxt.services.it.IssueTrackerService;
import com.fxlabs.fxt.services.notify.NotificationService;
import com.fxlabs.fxt.services.project.EnvironmentService;
import com.fxlabs.fxt.services.project.JobService;
import com.fxlabs.fxt.services.project.ProjectService;
import com.fxlabs.fxt.services.project.TestSuiteService;
import com.fxlabs.fxt.services.run.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(EVENTS_BASE)
@Validated
public class EventController {

    private RemoteEventService eventService;

    public static final Sort DEFAULT_SORT = new Sort(Sort.Direction.DESC,  "modifiedDate");

    @Autowired
    public EventController(RemoteEventService eventService) {
        this.eventService = eventService;
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public SseEmitter register() {
        return eventService.register(SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/orgevents", method = RequestMethod.GET)
    public Response<List<Event>> getRecentOrgEvents() {
        return eventService.getRecentOrgEvents(SecurityUtil.getOrgId(), PageRequest.of(0, 10, DEFAULT_SORT));
    }

}
