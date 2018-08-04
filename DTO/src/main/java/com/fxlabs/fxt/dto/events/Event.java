package com.fxlabs.fxt.dto.events;

import com.fxlabs.fxt.dto.alerts.*;
import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Event extends BaseDto<String> {

    private Type eventType;

    private Entity entityType;

    private Status status;

    private String entityId;
    private String name;

    private String link;

    private String user;

    private NameDto org;


}
