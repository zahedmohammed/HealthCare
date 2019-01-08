package com.fxlabs.issues.dto.account;

import com.fxlabs.issues.dto.base.BaseDto;
import com.fxlabs.issues.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RecentTransaction extends BaseDto<String> {

    private String location;
    private String description;
    private boolean confirmed;
  //  private String contactName;

    // private NameDto users;

}
