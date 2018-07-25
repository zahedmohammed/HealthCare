package com.fxlabs.fxt.dao.entity.project.autocode;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author Mohammed Luqman Shareef
 * @author Mohammed Shoukath Ali
 *
 */

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Database implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name ;
    private String version;
    private String description;

    private boolean inactive;

}
