package com.fxlabs.fxt.codegen.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Database implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name ;
    private String version;
//    private String description;
//
//    private boolean inactive;

}
