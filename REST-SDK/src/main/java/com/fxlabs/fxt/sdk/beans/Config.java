package com.fxlabs.fxt.sdk.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    private String version = "1";

    private String name;

    private String description;

    private List<String> licenses;

    private List<Environment> environments;

    private List<Job> jobs;

}


