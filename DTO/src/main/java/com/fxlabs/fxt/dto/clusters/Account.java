package com.fxlabs.fxt.dto.clusters;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Mohammed Luqman Shareef
 * @since 3/20/2018
 * @author Mohammed Shoukath Ali
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseDto<String> {

    private String name;
    private String region;

    private String accessKey;
    private String secretKey;

    private NameDto org;

    private AccountType accountType;

    private String prop1;
    private String prop2;
    private String prop3;

    private List<String> allowedRegions;


}
