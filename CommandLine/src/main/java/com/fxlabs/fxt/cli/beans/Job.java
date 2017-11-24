package com.fxlabs.fxt.cli.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "Default";
    private String environment = "Default";
    private List<String> tags;
    private String region;

}
