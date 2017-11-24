package com.fxlabs.fxt.dao.repository;

import com.fxlabs.fxt.dao.entity.run.Run;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;

public interface RunRepository extends JpaRepository<Run, String> {

    @Lock(LockModeType.WRITE)
    Run findById(String id);

    @Query("SELECT MAX(runId) FROM Run WHERE projectJob.id LIKE ?1")
    Long findMaxRunId(String id);

    @Query("SELECT r FROM Run r WHERE r.task.status LIKE ?1")
    List<Run> findByStatus(String status);


}
