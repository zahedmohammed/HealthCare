package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeConfig;
import com.fxlabs.fxt.dao.entity.project.autocode.AutoCodeGenerator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mohammed Shoukath Ali
 */
public interface AutoCodeGeneratorRepository extends JpaRepository<AutoCodeGenerator, String> {

}
