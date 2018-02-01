package com.fxlabs.fxt.dao.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

//@SolrDocument(collection = "fx")
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Auth {

    private String name;

    private String authType; // login authType
    private String username;
    private String password;


}

