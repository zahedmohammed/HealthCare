package com.fxlabs.fxt.sdk.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Intesar Shannan Mohammed
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Auth implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "Default";
    private String authType = "BASIC";
    private String username;
    private String password;

}
