package com.fxlabs.fxt.dto.run;

import com.fxlabs.fxt.dto.project.TestSuiteCategory;
import com.fxlabs.fxt.dto.project.TestSuiteSeverity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tests", "failed", "size", "time", "category" , "severity"})
public class Suite implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String runId;
    private String suiteName;

    private Long tests = new Long(0);
    private Long failed = new Long(0);
    private Long size = new Long(0);
    private Long time = new Long(0);

    private TestSuiteCategory category;
    private TestSuiteSeverity severity;
}
