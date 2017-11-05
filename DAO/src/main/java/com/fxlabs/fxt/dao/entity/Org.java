package com.fxlabs.fxt.dao.entity;

import javax.persistence.Entity;

//@SolrDocument(collection = "fx")
@Entity
public class Org extends BaseEntity<String> {


    private String name;
    private String company;
    private String location;


}

