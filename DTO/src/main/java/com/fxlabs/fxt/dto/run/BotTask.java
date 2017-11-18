package com.fxlabs.fxt.dto.run;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BotTask implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String endpoint;
    private String method;
    private String username;
    private String password;
    private List<String> request;
    private String response;
    private List<String> assertions;
    private String logs;
    private Date requestStartTime;
    private Date requestEndTime;
    private Long requestTime;
    private String result;

}