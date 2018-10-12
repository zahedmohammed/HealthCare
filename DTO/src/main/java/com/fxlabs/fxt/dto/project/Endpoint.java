package com.fxlabs.fxt.dto.project;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Mohammed Luqman Shareef
 */
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Endpoint implements Serializable{

    public Endpoint() {
    }

    public Endpoint(String endpoint) {
        this.endpoint = endpoint;
    }


    public Endpoint(String endpoint, String method) {
        this.endpoint = endpoint;
        this.method = method;
    }

    private String projectId;
    private String endpoint;
    private String method;
}

