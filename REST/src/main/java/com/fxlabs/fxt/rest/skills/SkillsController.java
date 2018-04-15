package com.fxlabs.fxt.rest.skills;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.skills.Skill;
import com.fxlabs.fxt.dto.skills.SkillType;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.skills.SkillService;
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
@RequestMapping(SKILLS_BASE)
public class SkillsController {

    private SkillService service;

    @Autowired
    public SkillsController(SkillService service) {
        this.service = service;
    }

    @Secured(ROLE_USER)
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Skill>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                         @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findAll(SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
    public Response<List<Skill>> findByType(@PathVariable("type") SkillType skillType,
                                            @RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                            @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {
        return service.findByType(skillType, SecurityUtil.getCurrentAuditor(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured(ROLE_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Skill> findById(@PathVariable("id") String id) {
        return service.findById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Skill> add(@RequestBody Skill request) {
        return service.save(request, SecurityUtil.getCurrentAuditor());
    }


    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<Skill> update(@Valid @RequestBody Skill dto) {
        return service.save(dto, SecurityUtil.getCurrentAuditor());
    }

    @Secured(ROLE_ENTERPRISE_ADMIN)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Skill> delete(@PathVariable("id") String id) {
        return service.delete(id, SecurityUtil.getCurrentAuditor());
    }


}
