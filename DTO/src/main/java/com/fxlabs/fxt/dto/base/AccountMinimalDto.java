package com.fxlabs.fxt.dto.base;

import com.fxlabs.fxt.dto.clusters.AccountType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountMinimalDto extends IdDto {

    private static final long serialVersionUID = 1L;

    private String name;
    private String region;

    private NameDto org;

    private AccountType accountType;

}
