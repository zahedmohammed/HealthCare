package com.fxlabs.issues.dto.account;

import com.fxlabs.issues.dto.base.BaseDto;
import com.fxlabs.issues.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SavingsAccount extends BaseDto<String> {
    private int accountNumber;
    private BigDecimal accountBalance;
    private NameDto user;

}