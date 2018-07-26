package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.Project;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mohammed Shoukath Ali
 */
public interface AutoCodeConfigRepository extends JpaRepository<AutoCodeConfig, String> {

}
