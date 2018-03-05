package com.fxlabs.fxt.dto.skills;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
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
public class SkillSubscription extends BaseDto<String> {

    private NameDto org;

    private String name;
    private String description;

    private SkillType skillType;

    private Skill skill;

    private List<Opt> opts;

}

