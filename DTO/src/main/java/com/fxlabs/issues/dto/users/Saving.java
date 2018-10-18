package com.fxlabs.issues.dto.users;

import com.fxlabs.issues.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Saving extends BaseDto<String> {

    private int calMonths;
    private int count;
    private int licenseSaving;
    private int managedInstanceSaving;
    private Integer total;
    private Date startDate;

}

