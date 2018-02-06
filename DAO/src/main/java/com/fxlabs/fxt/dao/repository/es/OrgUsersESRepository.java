package com.fxlabs.fxt.dao.repository.es;

import com.fxlabs.fxt.dao.entity.users.OrgUsers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface OrgUsersESRepository extends ElasticsearchRepository<OrgUsers, String> {
}
