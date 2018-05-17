package com.fxlabs.fxt.dto.notify;

import com.fxlabs.fxt.dto.base.BaseDto;
import com.fxlabs.fxt.dto.base.NameDto;
import com.fxlabs.fxt.dto.clusters.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Mohammed Luqman Shareef
 * @since 4/3/2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Notification extends BaseDto<String> {

    private String name;

    private String accessKey;
    private String secretKey;

    private String token;
    private String channel;

    private NameDto org;

    private Account account;

    private NotificationType type;

    private NotificationVisibility visibility;


}