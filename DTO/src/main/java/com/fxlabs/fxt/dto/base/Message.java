package com.fxlabs.fxt.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private MessageType type;
    private String key;
    private String value;
}
