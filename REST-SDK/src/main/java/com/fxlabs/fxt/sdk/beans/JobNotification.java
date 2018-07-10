package com.fxlabs.fxt.sdk.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Mohammed Shoukath Ali
 *
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobNotification {


    private String name;
    private String channel;
    private String to;
    private String account = "Default_Slack";
}
