package com.fxlabs.fxt.dto.alerts;

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
public class Alert extends BaseDto<String> {

    private TaskType taskType;

    private TaskState taskState;

    private AlertType type;

    private AlertStatus status;

    private AlertRefType refType;

    private String refId;
    private String refName;

    private String subject;
    private String message;

    private Date readDate;
    private Date healedDate;

    private List<String> users = new ArrayList<>();

    private NameDto org;


}
