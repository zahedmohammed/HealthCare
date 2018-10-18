package com.fxlabs.issues.dao.repository.es;

import com.fxlabs.issues.dao.entity.users.OrgUsers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgUsersESRepository extends ElasticsearchRepository<OrgUsers, String> {
}
