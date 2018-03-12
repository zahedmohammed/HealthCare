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
public class Skill extends BaseDto<String> {

    private NameDto org;

    private String name;
    private String description;

    private SkillType skillType;

    private String key;
    private String accessKey;
    private String secretKey;
    private String host;

    private String prop1;
    private String prop2;
    private String prop3;
    private String prop4;
    private String prop5;

    private List<Opt> opts;

}
