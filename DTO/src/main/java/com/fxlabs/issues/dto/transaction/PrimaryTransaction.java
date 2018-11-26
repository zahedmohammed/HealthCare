package com.fxlabs.issues.dto.transaction;

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
public class PrimaryTransaction extends BaseDto<String> {
    private String description;
    private String type;
    private String status;
    private double amount;
    private BigDecimal availableBalance;
    private NameDto user;

}
