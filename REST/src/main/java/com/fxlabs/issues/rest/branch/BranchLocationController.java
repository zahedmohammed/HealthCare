package com.fxlabs.issues.rest.branch;

import com.fxlabs.issues.dto.base.Response;
import com.fxlabs.issues.dto.branch.Branch;
import com.fxlabs.issues.rest.base.BaseController;
import com.fxlabs.issues.rest.base.SecurityUtil;
import com.fxlabs.issues.services.branch.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.fxlabs.issues.rest.base.BaseController.*;

@RestController
@RequestMapping(BaseController.BRANCH_BASE)
public class BranchLocationController {

    private BranchService branchService;


    @Autowired
    public BranchLocationController(
            BranchService branchService) {
        this.branchService = branchService;
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<Branch> findBranchById(@PathVariable("id") String id) {


        return branchService.findBranchById(id, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, ROLE_USER, ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response<List<Branch>> findAllBranches() {


        return branchService.findAllBranches(SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<Branch> add(@RequestBody Branch request) {
        return branchService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Response<Branch> update(@RequestBody Branch request) {
        return branchService.save(request, SecurityUtil.getCurrentAuditor());
    }

    @Secured({ROLE_PROJECT_MANAGER, BaseController.ROLE_USER, BaseController.ROLE_ADMIN})
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<Branch> deleteById(@PathVariable("id") String id) {
        return branchService.delete(id, SecurityUtil.getCurrentAuditor());
    }
}
