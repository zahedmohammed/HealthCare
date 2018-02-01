package com.fxlabs.fxt.dao.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

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

}

