package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ClusterESRepository extends ElasticsearchRepository<Cluster, String> {

}
