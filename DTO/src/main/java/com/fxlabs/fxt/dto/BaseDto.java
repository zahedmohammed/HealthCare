package com.fxlabs.fxt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto<U> {
    private static final long serialVersionUID = 1L;

    private String id;

    private U createdBy;

    private long createdDate;

    private U modifiedBy;

    private long modifiedDate;
}
