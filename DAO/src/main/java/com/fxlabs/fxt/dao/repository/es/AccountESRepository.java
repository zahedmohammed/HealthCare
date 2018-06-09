package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.clusters.Account;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
public interface AccountESRepository extends ElasticsearchRepository<Account, String> {
}
