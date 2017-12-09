package com.fxlabs.fxt.dto.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<D> implements Serializable {
    private String requestId = "None";
    private Date requestTime = new Date();
    private boolean errors = false;
    private List<Message> messages;
    private D data;
    private Integer totalPages = 1;
    private Long totalElements = 1L;

    public Response(D data) {
        this.requestId = "None";
        this.data = data;
        requestTime = new Date();
    }

    public Response(D data, Long totalElements, Integer totalPages) {
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Message implements Serializable {
    private MessageType type;
    private String key;
    private String value;
}

enum MessageType {
    INFO, ERROR
}
