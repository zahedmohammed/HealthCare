package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import com.fxlabs.fxt.dao.entity.clusters.ClusterVisibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 */
public interface ClusterRepository extends JpaRepository<Cluster, String> {

    Optional<Cluster> findByNameAndOrgId(String name, String orgId);

    Optional<Cluster> findByNameAndOrgName(String name, String orgName);

    Page<Cluster> findByOrgIdIn(Collection<String> orgs, Pageable pageable);

    Long countByOrgId(String orgId);

    List<Cluster> findByAccountIdAndInactive(String id, boolean inactive);

    Optional<Cluster> findByKey(String key);

}
