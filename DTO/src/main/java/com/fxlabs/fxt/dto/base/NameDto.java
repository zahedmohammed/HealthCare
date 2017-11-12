package com.fxlabs.fxt.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class NameDto extends IdDto {
    private static final long serialVersionUID = 1L;
    private String name;

}
