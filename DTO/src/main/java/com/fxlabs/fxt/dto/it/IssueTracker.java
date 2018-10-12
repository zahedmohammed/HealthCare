package com.fxlabs.fxt.dto.it;

import com.fxlabs.fxt.dto.base.AccountMinimalDto;
import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.base.Visibility;
import com.fxlabs.fxt.dto.skills.Opt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IssueTracker extends BaseDto<String> {

    private NameDto org;

    private String name;
    private String description;

    private NameDto skill;

    private String prop1;
    private String prop2;
    private String prop3;
    private String prop4;
    private String prop5;

    private List<Opt> opts;

    private State state;

    private AccountMinimalDto account;

    private Visibility visibility;

}

