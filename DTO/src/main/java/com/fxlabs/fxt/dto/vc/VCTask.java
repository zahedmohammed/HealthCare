package com.fxlabs.fxt.dto.vc;

import com.fxlabs.fxt.dto.project.AutoCodeConfig;
import com.fxlabs.fxt.dto.project.GenPolicy;
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
public class VCTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private String projectId;
    private String projectName;

    private GenPolicy genPolicy;
    private String openAPISpec;

    private String fxUrl;
    private String projectUser;
    private String projectGrant;

    private AutoCodeConfig autoCodeConfig;

    private String vcUrl;
    private String vcUsername;
    private String vcPassword;
    private String vcBranch;
    private String vcLastCommit;
    private List<String> categories;
    private boolean deleteAll = false;


}
