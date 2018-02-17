package com.fxlabs.fxt.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Intesar Shannan Mohammed
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class BaseDto<U> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private U createdBy;

    private Date createdDate;

    private U modifiedBy;

    private Date modifiedDate;

    private Long version;

    private boolean inactive;
}
