package com.fxlabs.fxt.dto.notification;

import com.fxlabs.fxt.dto.cloud.CloudTaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohammed Shoukath Ali
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotificationTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Map<String, String> opts = new HashMap<>();


}
