package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.clusters.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * @author Intesar Shannan Mohammed
 */
public interface ClusterRepository extends JpaRepository<Cluster, String> {

    Optional<Cluster> findByNameAndOrgId(String name, String orgId);

    Optional<Cluster> findByNameAndOrgName(String name, String orgName);

}
