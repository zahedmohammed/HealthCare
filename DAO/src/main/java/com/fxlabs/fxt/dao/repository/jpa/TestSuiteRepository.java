package com.fxlabs.fxt.dao.repository.jpa;

import com.fxlabs.fxt.dao.entity.project.TestSuite;
import com.fxlabs.fxt.dao.entity.project.TestSuiteCategory;
import com.fxlabs.fxt.dao.entity.project.TestSuiteCount;
import com.fxlabs.fxt.dao.entity.project.TestSuiteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Intesar Shannan Mohammed
 */
public interface TestSuiteRepository extends JpaRepository<TestSuite, String> {

    Stream<TestSuite> findByProjectIdAndType(String projectId, TestSuiteType type);

    Long countByProjectIdAndType(String projectId, TestSuiteType type);

    TestSuite findByProjectIdAndTypeAndName(String projectId, TestSuiteType type, String name);

    Optional<TestSuite> findByProjectIdAndName(String projectId, String name);

    Long countByProjectIdAndInactive(String project, boolean inactive);

    Long countByProjectIdAndAutoGeneratedAndInactive(String project, boolean autoGenerated, boolean inactive);

    @Query("SELECT " +
            "    new com.fxlabs.fxt.dao.entity.project.TestSuiteCount(ts.method , count(ts)) " +
            "FROM " +
            "    TestSuite ts " +
            "WHERE " +
            "    ts.project.id = ?1 AND ts.autoGenerated = ?2 AND ts.inactive = false " +
            "GROUP BY " +
            "    ts.method")
    List<TestSuiteCount> countByMethodTypeAndAutoGen(String project, boolean auto);

    @Query("SELECT " +
            "    new com.fxlabs.fxt.dao.entity.project.TestSuiteCount(ts.category , count(ts)) " +
            "FROM " +
            "    TestSuite ts " +
            "WHERE " +
            "    ts.project.id = ?1 AND ts.autoGenerated = ?2 AND ts.inactive = false " +
            "GROUP BY " +
            "    ts.category")
    List<TestSuiteCount> countByCategoryAndAutoGen(String project, boolean auto);

    @Query("SELECT " +
            "    new com.fxlabs.fxt.dao.entity.project.TestSuiteCount(ts.severity , count(ts)) " +
            "FROM " +
            "    TestSuite ts " +
            "WHERE " +
            "    ts.project.id = ?1 AND ts.autoGenerated = ?2 AND ts.inactive = false " +
            "GROUP BY " +
            "    ts.severity")
    List<TestSuiteCount> countBySeverityAndAutoGen(String project, boolean auto);

    Long countTestCasesByProjectIdAndAutoGeneratedAndInactive(String project, boolean autoGenerated, boolean inactive);

}
