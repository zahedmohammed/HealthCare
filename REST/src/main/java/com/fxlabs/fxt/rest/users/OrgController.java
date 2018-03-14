package com.fxlabs.fxt.rest.users;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.users.Org;
import com.fxlabs.fxt.dto.users.OrgUsers;
import com.fxlabs.fxt.dto.users.SystemSetting;
import com.fxlabs.fxt.dto.users.Users;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.users.OrgUsersService;
import com.fxlabs.fxt.services.users.UsersService;
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
@RequestMapping(ORG_BASE)
public class OrgController {

    private OrgUsersService orgUsersService;

    @Autowired
    public OrgController(
            OrgUsersService orgUsersService) {
        this.orgUsersService = orgUsersService;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<OrgUsers>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                                 @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return orgUsersService.findByAccess(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

}
