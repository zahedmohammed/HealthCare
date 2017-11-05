package com.fxlabs.fxt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseDto<String> {


    private String name;
    private String username;
    private String email;
    private String company;
    private String location;
    private String jobTitle;
    //private Org org;


}

