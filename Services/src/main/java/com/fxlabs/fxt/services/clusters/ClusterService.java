package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ClusterService extends GenericService<Cluster, String> {

    Response<Cluster> create(Cluster dto, String user);

    Response<Cluster> update(Cluster dto, String user);

    Response<List<Cluster>> findAll(String user, Pageable pageable);

    Response<Cluster> findByName(String id, String user);

    Response<Cluster> findById(String id, String user);

    Response<Cluster> delete(String s, String user);

    Response<Long> countBotRegions(String user);
}
