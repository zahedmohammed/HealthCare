package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RunRepository extends JpaRepository<Run, String> {

    // TODO - Replace this with job updating RunTask status
    //@Lock(LockModeType.WRITE)
    @Query("SELECT r FROM Run r WHERE r.id LIKE ?1")
    Run findByRunId(String id);

    @Query("SELECT MAX(runId) FROM Run WHERE job.id LIKE ?1")
    Long findMaxRunId(String id);

    @Query("SELECT r FROM Run r WHERE r.task.status LIKE ?1")
    List<Run> findByStatus(TaskStatus status);


}
