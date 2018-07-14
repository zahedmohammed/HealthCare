package com.fxlabs.fxt.codegen.code;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match implements Serializable {
    private String name;
    private String value = "999";
    //private Type type; // integer - supported // TODO
    //private Method method; // get - supported // TODO
    //private In in; // query - supported // TODO
}

enum In {
    query, body
}

enum Type {
    string, integer
}

enum Method {
    get, post, put, delete
}
