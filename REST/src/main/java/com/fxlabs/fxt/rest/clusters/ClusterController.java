package com.fxlabs.fxt.rest.clusters;

import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.rest.base.BaseController;
import com.fxlabs.fxt.services.clusters.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fxlabs.fxt.rest.base.BaseController.CLUSTER_BASE;

/**
 * @author Intesar Shannan Mohammed
 */
@RestController
@RequestMapping(CLUSTER_BASE)
public class ClusterController extends BaseController<Cluster, String> {

    private ClusterService clusterService;

    @Autowired
    public ClusterController(
            ClusterService clusterService) {
        super(clusterService);
        this.clusterService = clusterService;
    }

}
