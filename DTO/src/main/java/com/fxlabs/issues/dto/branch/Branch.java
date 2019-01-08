package com.fxlabs.issues.dto.branch;

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
public class Branch extends BaseDto<String> {

    private String access;
    private String atmAtBranch;
    private String branchName;
    private String branchMediatedServiceName;
    private String branchType;
    private String customerSegment;
    private String faxNumber;

 //   NameDto address;


}
