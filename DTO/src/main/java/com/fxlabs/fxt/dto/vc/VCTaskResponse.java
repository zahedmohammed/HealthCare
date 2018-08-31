package com.fxlabs.fxt.dto.vc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VCTaskResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String projectId;
    private String projectName;

    private String logs;
    private boolean success;

    private String path;

    private String vcLastCommit;

    private int autoGenSuitesCount;

    private List<String> apiEndpoints;

    private String taskId;

}
