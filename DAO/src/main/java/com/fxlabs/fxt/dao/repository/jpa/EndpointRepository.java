package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 */
public interface EndpointRepository extends JpaRepository<Endpoint, String> {

    List<Endpoint> findByProjectId(String projectId);
}
