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
public class PingTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Map<String, String> opts = new HashMap<>();


}
