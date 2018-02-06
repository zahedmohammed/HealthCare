package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ClusterESRepository extends ElasticsearchRepository<Cluster, String> {

    List<Cluster> findByVisibility(ClusterVisibility visibility);
}
