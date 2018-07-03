package com.fxlabs.fxt.rest.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Saving;
import com.fxlabs.fxt.dto.users.SystemSetting;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.users.SystemSettingService;
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
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(SYSTEM_SETTING_BASE)
@Validated
public class SystemSettingController {

    private SystemSettingService service;

    @Autowired
    public SystemSettingController(
            SystemSettingService service) {
        this.service = service;
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<SystemSetting>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) @Min(0) Integer page,
                                                 @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_MAX_PAGE_SIZE_VALUE, required = false) @Max(100) Integer pageSize) {
        return service.findAll(null, PageRequest.of(page, pageSize));
    }


    @Secured({ROLE_ENTERPRISE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<SystemSetting> findById(@PathVariable("id") String id) {
        return service.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_ENTERPRISE_ADMIN, ROLE_ADMIN})
    @RequestMapping(value = "/{id}/bot-saving", method = RequestMethod.GET)
    public Response<Saving> getSavingsById(@PathVariable("id") String id) {
        return service.getSavingsById(id);
    }

    /*@Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/batch-save", method = RequestMethod.POST)
    public Response<Boolean> create(@Valid @RequestBody List<SystemSetting> dtos) {
        return service.save(dtos);
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<SystemSetting> create(@Valid @RequestBody SystemSetting dto) {
        return service.save(dto);
    }*/

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<SystemSetting> update(@Valid @RequestBody SystemSetting dto) {
        return service.save(dto);
    }

    /*@Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<SystemSetting> delete(@PathVariable("id") String id) {
        return service.delete(id, SecurityUtil.getCurrentAuditor());
    }*/

}
