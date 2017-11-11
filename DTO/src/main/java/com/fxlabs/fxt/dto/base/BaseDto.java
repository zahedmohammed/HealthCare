package com.fxlabs.fxt.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto<U> {
    private static final long serialVersionUID = 1L;

    private String id;

    private U createdBy;

    private Date createdDate;

    private U modifiedBy;

    private Date modifiedDate;
}
