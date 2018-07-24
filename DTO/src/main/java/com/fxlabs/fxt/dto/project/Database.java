package com.fxlabs.fxt.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Mohammed Luqman Shareef
 * @author Mohammed Shoukath Ali
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
    private String description;

    private boolean inactive;

}
