package com.fxlabs.fxt.rest.notify;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.notify.NotificationAccount;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.notify.NotificationAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.*;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 */
@RestController
@RequestMapping(NOTIFICATION_ACCOUNT_BASE)
public class NotificationAccountController {

    private NotificationAccountService NotificationAccountService;

    @Autowired
    public NotificationAccountController(
            NotificationAccountService NotificationAccountService) {
        this.NotificationAccountService = NotificationAccountService;
    }

    @Secured(ROLE_USER)
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<NotificationAccount>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return NotificationAccountService.findAll(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<NotificationAccount> findById(@PathVariable("id") String id) {
        return NotificationAccountService.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<NotificationAccount>> create(@Valid @RequestBody List<NotificationAccount> dtos) {
        //return service.save(dtos);
        return null;
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<NotificationAccount> create(@Valid @RequestBody NotificationAccount dto) {
        return NotificationAccountService.create(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<NotificationAccount> update(@Valid @RequestBody NotificationAccount dto) {
        return NotificationAccountService.update(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<NotificationAccount> delete(@PathVariable("id") String id) {
        return NotificationAccountService.delete(id, SecurityUtil.getCurrentAuditor());
    }


}
