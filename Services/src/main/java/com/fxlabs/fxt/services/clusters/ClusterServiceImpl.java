package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.converters.clusters.ClusterConverter;
import com.fxlabs.fxt.dao.repository.es.ClusterESRepository;
import com.fxlabs.fxt.dao.repository.jpa.ClusterRepository;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.services.base.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Intesar Shannan Mohammed
 */
@Service
@Transactional
public class ClusterServiceImpl extends GenericServiceImpl<com.fxlabs.fxt.dao.entity.clusters.Cluster, Cluster, String> implements ClusterService {

    private ClusterRepository clusterRepository;
    private ClusterESRepository clusterESRepository;
    private ClusterConverter clusterConverter;

    @Autowired
    public ClusterServiceImpl(ClusterRepository clusterRepository, ClusterESRepository clusterESRepository,
                              ClusterConverter clusterConverter) {

        super(clusterRepository, clusterConverter);

        this.clusterRepository = clusterRepository;
        this.clusterESRepository = clusterESRepository;
        this.clusterConverter = clusterConverter;

    }

}
