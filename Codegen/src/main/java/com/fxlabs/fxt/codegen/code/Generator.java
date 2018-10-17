package com.fxlabs.fxt.codegen.code;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @author Intesar Shannan Mohammed
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "type")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)

public class Generator implements Serializable {

    private String postfix;
    private String type;
    private List<String> assertions;
    private List<String> tags;
    private List<String> logForgingPatterns;
    private String severity;
    private boolean inactive;
    private List<Match> matches;
    private Database database;
}