package com.fxlabs.fxt.dto.git;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GitTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private String projectId;
    private String gitUrl;
    private String gitUsername;
    private String gitPassword;
    private String gitBranch;
    private String gitLastCommit;

}
