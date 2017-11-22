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
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    private String version = "1";

    //private FxService fxService;

    private TestApp testApp;


}


