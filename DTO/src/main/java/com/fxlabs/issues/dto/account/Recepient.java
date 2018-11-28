package com.fxlabs.issues.dto.account;

import com.fxlabs.issues.dto.base.BaseDto;
import com.fxlabs.issues.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class Recepient extends BaseDto<String> {

    private String name;
    private String email;
    private String phone;
    private String accountNumber;
    private String description;
    private NameDto user;

}
