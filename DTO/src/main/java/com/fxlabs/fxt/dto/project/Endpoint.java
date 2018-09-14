package com.fxlabs.fxt.dto.project;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.clusters.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

