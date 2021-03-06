package com.fxlabs.issues.dto.users;


import com.fxlabs.issues.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Org extends BaseDto<String> {


    private String name;
    private String description;

    private OrgType orgType; // PERSONAL | TEAM | ENTERPRISE
    private String billingEmail;

    private String company;
    private String location;

    private OrgPlan orgPlan; // FREE | PRO | UNLIMITED | PAY-AS-YOU-GO

    // TODO Billing Info


}

