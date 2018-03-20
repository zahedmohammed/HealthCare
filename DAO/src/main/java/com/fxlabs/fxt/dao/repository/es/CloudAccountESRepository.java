package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.clusters.CloudAccount;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
public interface CloudAccountESRepository extends ElasticsearchRepository<CloudAccount, String> {

    List<CloudAccount> findByVisibility(ClusterVisibility visibility);
}
