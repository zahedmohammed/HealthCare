package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.run.Run;
import com.fxlabs.fxt.dao.entity.run.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    List<Run> findByStatus(TaskStatus status, Pageable pageable);

    Stream<Run> findByTaskStatusAndCreatedDateLessThan(TaskStatus status, Date dt);

    Stream<Run> findByTaskStatusInAndCreatedDateGreaterThan(Collection<TaskStatus> status, Date dt);

    List<Run> findAllRunsByJobId(String id);

    Page<Run> findByJobId(String id, Pageable pageable);

    Optional<Run> findByJobIdAndRunId(String jobId, Long runId);

    @Query("SELECT SUM(r.task.totalTestCompleted) FROM Run r WHERE r.job.project.id LIKE ?1")
    Long countTestsByProject(String projectId);

    @Query("SELECT SUM(r.task.totalTestCompleted + r.task.failedTests) FROM Run r WHERE r.createdDate>?2 and  r.job.project.id LIKE ?1")
    Long countTestsByProjectAndCreatedDateGreaterThan(String projectId, Date date);

    @Query("SELECT SUM(r.task.totalBytes) FROM Run r WHERE r.job.project.id LIKE ?1")
    Long countBytesByProject(String projectId);

    @Query("SELECT SUM(r.task.totalBytes) FROM Run r WHERE r.createdDate>?2 and r.job.project.id LIKE ?1")
    Long countBytesByProjectAndCreatedDateGreaterThan(String projectId, Date date);

    @Query("SELECT SUM(r.task.totalTime) FROM Run r WHERE r.job.project.id LIKE ?1")
    Long countTimeByProject(String projectId);

    @Query("SELECT SUM(r.task.totalTime) FROM Run r WHERE r.createdDate>?2 and r.job.project.id LIKE ?1")
    Long countTimeByProjectAndCreatedDateGreaterThan(String projectId, Date date);

    Long countByJobProjectId(String project);

    Long countByJobProjectIdAndCreatedDateGreaterThan(String project, Date createDate);


}
