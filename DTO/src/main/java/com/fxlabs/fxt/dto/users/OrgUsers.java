package com.fxlabs.fxt.dto.users;

import com.fxlabs.fxt.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@SolrDocument(collection = "fx")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrgUsers extends BaseDto<String> {

    private Org org;

    private Users users;

    private String role; // USER | ADMIN

    private String status; // INVITE_SENT | ACTIVE | DECLINED


}

