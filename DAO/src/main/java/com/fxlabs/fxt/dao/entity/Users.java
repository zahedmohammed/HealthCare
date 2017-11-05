package com.fxlabs.fxt.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

//@SolrDocument(collection = "fx")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseEntity<String> {


    private String name;
    private String username;
    private String email;
    private String company;
    private String location;
    private String jobTitle;
    //private Org org;


}

