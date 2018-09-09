package com.fxlabs.fxt.services.clusters;

import com.fxlabs.fxt.dto.base.Response;
import com.fxlabs.fxt.dto.clusters.Cluster;
import com.fxlabs.fxt.dto.users.Saving;
import com.fxlabs.fxt.services.base.GenericService;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface ClusterService extends GenericService<Cluster, String> {

    Response<Cluster> create(Cluster dto, String org);

    Response<Cluster> update(Cluster dto, String org, String user);

    Response<List<Cluster>> findAll(String user, Pageable pageable);

    Response<List<Cluster>> findEntitled(Collection<String> orgs, Pageable pageable);

    Response<Cluster> findByName(String name, String user);

    Response<Cluster> findById(String id, String user);

    Response<Cluster> addExecBot(Cluster dto, String user);

    Response<Cluster> delete(String s, String org, String user);

    Response<Long> countBotRegions(String user);

    Response<Cluster> deleteExecBot(Cluster dto, String user);

    Response<String> pingExecBot(String id, String o);

    Response<Saving> savings(String id, String o);

    public Response<Cluster> findByNameAndOrgId(String name, String orgId);
}
