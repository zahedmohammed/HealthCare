package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Intesar Shannan Mohammed
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Policies implements Serializable {

    private static final long serialVersionUID = 1L;

    private String initExec = "Request"; // Request | Suite
    private String cleanupExec = "Request"; // Request | Suite
    private String logger = "DEBUG"; // ERROR | DEBUG
    private Long timeoutSeconds = 300L;
    private Integer repeatOnFailure = 0;
    private Integer repeat = 0;
    private String repeatModule;
    private Long repeatDelay = 0L; //ms

}

