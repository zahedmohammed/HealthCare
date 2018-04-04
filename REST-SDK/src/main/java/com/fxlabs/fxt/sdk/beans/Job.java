package com.fxlabs.fxt.sdk.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 * @author Mohammed Shoukath Ali
 *
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name = "Default";
    private String refId;
    private String description;

    private String environment = "Default";
    private List<String> tags;
    private String regions;

    private String cron;
    private Date nextFire;

    private String issueTracker;

    private boolean inactive;

    private  List<String> notifications;


}
