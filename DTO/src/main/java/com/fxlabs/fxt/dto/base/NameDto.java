package com.fxlabs.fxt.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class NameDto extends IdDto {
    private static final long serialVersionUID = 1L;
    private String name;

}
