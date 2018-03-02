package com.fxlabs.fxt.dto.it;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ITTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private String projectId;
    private String projectName;

    private String fxUrl;


    private String itUrl;
    private String itUsername;
    private String itPassword;
    private String itProject;



}
