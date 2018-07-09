package com.fxlabs.fxt.codegen.code;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestSuite implements Serializable {

    private String postfix;
    private List<String> assertions;


}
