package com.fxlabs.fxt.dto.cloud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CloudTaskResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Boolean success;
    private String logs;
    private String keys;


}
