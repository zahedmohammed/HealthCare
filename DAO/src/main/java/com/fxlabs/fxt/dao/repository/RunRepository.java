package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.run.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface RunRepository extends JpaRepository<Run, String> {

    @Lock(LockModeType.WRITE)
    Run findById(String id);
}
