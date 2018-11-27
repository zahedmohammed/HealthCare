package com.fxlabs.issues.dto.branch;

import com.fxlabs.issues.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Address extends BaseDto<String> {

    private String lattitude;
    private String longitude;
    private String doorNo;
    private String streetName;
    private String locality;
    private String city;

}
