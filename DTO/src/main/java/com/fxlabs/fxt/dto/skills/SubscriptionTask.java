package com.fxlabs.fxt.dto.skills;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.it.IssueTracker;
import com.fxlabs.fxt.dto.skills.TaskResult;
import com.fxlabs.fxt.dto.skills.TaskStatus;
import com.fxlabs.fxt.dto.skills.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SubscriptionTask extends BaseDto<String> {

    private IssueTracker subscription;

    private TaskType type;

    private TaskResult result;

    private TaskStatus status;

    private String logs;

    private String clusterId;

}

