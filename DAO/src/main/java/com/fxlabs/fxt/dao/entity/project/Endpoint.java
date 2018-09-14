package com.fxlabs.fxt.dao.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author Mohammed Luqman Shareef
 */
@Embeddable
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Endpoint implements Serializable {


    public Endpoint(){}

    public Endpoint(String endpoint){
        this.endpoint = endpoint;
    }

    public Endpoint(String endpoint, String method){
        this.endpoint = endpoint;
        this.method = method;
    }



    private String endpoint;
    private String method;

}

