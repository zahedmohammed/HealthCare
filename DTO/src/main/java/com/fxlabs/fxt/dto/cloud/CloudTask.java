package com.fxlabs.fxt.dto.cloud;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CloudTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    // TODO - read [queue, url, u, p, image, size] - Skill
    // TODO - read [task-id, count, group-name, connect-queue, cmd] -> Subscription

    private Map<String, String> opts = new HashMap<>();


}
