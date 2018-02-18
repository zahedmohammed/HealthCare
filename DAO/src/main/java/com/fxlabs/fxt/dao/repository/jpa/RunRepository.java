package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface RunRepository extends JpaRepository<Run, String> {

    // TODO - Replace this with job updating RunTask status
    //@Lock(LockModeType.WRITE)
    @Query("SELECT r FROM Run r WHERE r.id LIKE ?1")
    Run findByRunId(String id);

    @Query("SELECT MAX(runId) FROM Run WHERE job.id LIKE ?1")
    Long findMaxRunId(String id);

    @Query("SELECT r FROM Run r WHERE r.task.status LIKE ?1")
    Stream<Run> findByStatus(TaskStatus status);

    Stream<Run> findByTaskStatusAndCreatedDateLessThan(TaskStatus status, Date dt);

    Stream<Run> findByTaskStatusAndCreatedDateGreaterThan(TaskStatus status, Date dt);

    List<Run> findByJobId(String id, Pageable pageable);

    @Query("SELECT SUM(r.task.totalTestCompleted) FROM Run r WHERE r.job.project.id LIKE ?1")
    Long countTestsByProject(String projectId);


}
