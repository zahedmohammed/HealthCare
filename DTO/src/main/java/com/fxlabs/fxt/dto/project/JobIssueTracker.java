package com.fxlabs.fxt.dto.project;


import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.*;

import java.io.Serializable;

/**
 * @author Mohammed Shoukath Ali
 *
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class JobIssueTracker implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String url;
    private String account = "Default_GitHub";
}
