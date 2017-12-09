package com.fxlabs.fxt.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class IdDto extends BaseDto<String> {
    private static final long serialVersionUID = 1L;

}
