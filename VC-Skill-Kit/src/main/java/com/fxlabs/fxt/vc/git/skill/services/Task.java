package com.fxlabs.fxt.vc.git.skill.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    private String vcUrl;
    private String vcUsername;
    private String vcPassword;
    private String vcBranch;
    private String vcLastCommit;


}
