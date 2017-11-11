package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.run.Run;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunRepository extends JpaRepository<Run, String> {
}
