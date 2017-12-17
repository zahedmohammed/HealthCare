package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


//@SolrDocument(collection = "fx")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Policies {

    private String initExec = "Request"; // Request | Suite
    private String cleanupExec = "Request"; // Request | Suite

}

