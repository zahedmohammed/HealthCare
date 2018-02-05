package com.fxlabs.fxt.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmailTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> tos = new ArrayList<>();
    private String subject;
    private String body;
}
