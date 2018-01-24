package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


//@SolrDocument(collection = "fx")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Policies implements Serializable {

    private static final long serialVersionUID = 1L;

    private String initExec = "Request"; // Request | Suite
    private String cleanupExec = "Request"; // Request | Suite
    private String logger = "ERROR"; // ERROR | DEBUG

}

