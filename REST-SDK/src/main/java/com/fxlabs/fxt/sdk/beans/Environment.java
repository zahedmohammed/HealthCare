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
public class Environment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "Default";
    private String refId;
    private String description;
    private String baseUrl;
    private boolean inactive = false;

    private List<Auth> auths;


}