package com.fxlabs.fxt.dao.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/**
 * @author Intesar Shannan Mohammed
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Policies {

    private String initExec = "Request"; // Request | Suite
    private String cleanupExec = "Request"; // Request | Suite
    private String logger = "ERROR"; // ERROR | DEBUG
    private Long timeoutSeconds = 300L;

    private Integer repeatOnFailure = 0;
    private Integer repeat = 0;
    private String repeatModule; // importname
    private Long repeatDelay = 0L;

}

