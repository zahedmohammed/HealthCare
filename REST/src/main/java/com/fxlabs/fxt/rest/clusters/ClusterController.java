package com.fxlabs.fxt.rest.clusters;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.rest.base.SecurityUtil;
import com.fxlabs.fxt.services.clusters.ClusterService;
import com.fxlabs.fxt.services.users.OrgService;
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
@RequestMapping(CLUSTER_BASE)
public class ClusterController {

    private ClusterService clusterService;
    private OrgService orgService;

    @Autowired
    public ClusterController(
            ClusterService clusterService, OrgService orgService) {
        this.clusterService = clusterService;
        this.orgService = orgService;
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(method = RequestMethod.GET)
    public Response<List<Cluster>> findAll(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {

        return clusterService.findAll(SecurityUtil.getOrgId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/superbotnetwork", method = RequestMethod.GET)
    public Response<List<Cluster>> findSuperBotNetwork(@RequestParam(value = PAGE_PARAM, defaultValue = DEFAULT_PAGE_VALUE, required = false) Integer page,
                                           @RequestParam(value = PAGE_SIZE_PARAM, defaultValue = DEFAULT_PAGE_SIZE_VALUE, required = false) Integer pageSize) {

        return clusterService.findAll(orgService.findByName("FXLabs").getData().getId(), PageRequest.of(page, pageSize, DEFAULT_SORT));
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Cluster> findById(@PathVariable("id") String id) {

        return clusterService.findById(id, SecurityUtil.getOrgId());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public Response<List<Cluster>> create(@Valid @RequestBody List<Cluster> dtos) {
        //return service.save(dtos);
        return null;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Cluster> create(@Valid @RequestBody Cluster dto) {

        return clusterService.create(dto, SecurityUtil.getOrgId());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response<Cluster> update(@Valid @RequestBody Cluster dto) {

        return clusterService.update(dto, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Cluster> delete(@PathVariable("id") String id) {

        return clusterService.delete(id, SecurityUtil.getOrgId(), SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_USER, ROLE_PROJECT_MANAGER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}/ping", method = RequestMethod.GET)
    public Response<String> ping(@PathVariable("id") String id) {

        return clusterService.pingExecBot(id, SecurityUtil.getOrgId());
    }


}
