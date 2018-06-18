package com.fxlabs.fxt.rest.notify;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.notify.Notification;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.notify.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 */
@RestController
@RequestMapping(NOTIFICATION_BASE)
@Validated
public class NotificationController {

    private NotificationService notificationService;

    @Autowired
    public NotificationController(
            NotificationService NotificationAccountService) {
        this.notificationService = NotificationAccountService;
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Notification>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                                @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) @Max(20) Integer pageSize) {

        return notificationService.findAll(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Notification> findById(@PathVariable("id") String id) {

        return notificationService.findById(id, SecurityUtil.getOrgId());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<Notification>> create(@Valid @RequestBody List<Notification> dtos) {
        //return service.save(dtos);
        return null;
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Notification> create(@Valid @RequestBody Notification dto) {

        return notificationService.create(dto, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Notification> update(@Valid @RequestBody Notification dto) {

        return notificationService.update(dto, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Notification> delete(@PathVariable("id") String id) {

        return notificationService.delete(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }


}
