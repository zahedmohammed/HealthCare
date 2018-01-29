package com.fxlabs.fxt.dto.users;


import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    private String plan; // FREE | PRO | UNLIMITED | PAY-AS-YOU-GO

    // TODO Billing Info


}

