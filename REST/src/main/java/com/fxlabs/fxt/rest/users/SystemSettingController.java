package com.fxlabs.fxt.rest.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.SystemSetting;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.users.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.fxlabs.fxt.rest.base.BaseController.ROLE_ENTERPRISE_ADMIN;
import static com.fxlabs.fxt.rest.base.BaseController.SYSTEM_SETTING_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(SYSTEM_SETTING_BASE)
public class SystemSettingController {

    private SystemSettingService service;

    @Autowired
    public SystemSettingController(
            SystemSettingService service) {
        this.service = service;
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<SystemSetting>> findAll() {
        return service.findAll(null, PageRequest.of(0, 100));
    }


    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<SystemSetting> findById(@PathVariable("id") String id) {
        return service.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/batch-save", method = RequestMethod.POST)
    public Response<Boolean> create(@Valid @RequestBody List<SystemSetting> dtos) {
        return service.save(dtos);
    }

    /*@Secured(ROLE_ENTERPRISE_ADMIN)
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
