package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.clusters.CloudAccount;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 */
public interface CloudAccountRepository extends JpaRepository<CloudAccount, String> {

    Optional<CloudAccount> findByNameAndOrgId(String name, String orgId);

    Optional<CloudAccount> findByNameAndOrgName(String name, String orgName);

    List<CloudAccount> findByVisibility(ClusterVisibility visibility);

    Long countByVisibility(ClusterVisibility visibility);

}
