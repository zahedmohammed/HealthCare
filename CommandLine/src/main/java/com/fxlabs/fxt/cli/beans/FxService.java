package com.fxlabs.fxt.cli.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FxService implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ip;
    private String accessKey;
    private String secretKey;
}