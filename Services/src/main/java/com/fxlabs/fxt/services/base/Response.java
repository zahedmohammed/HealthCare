package com.fxlabs.fxt.services.base;

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
    private Long pages = 1L;
    private Long total = 1L;

    public Response(D data) {
        this.requestId = "None";
        this.data = data;
        requestTime = new Date();
        this.errors = false;
        this.pages = 1L;
        this.total = 1L;
    }

    public Response(D data, Long totalElements, Long totalPages) {
        this.data = data;
        this.pages = totalPages;
        this.total = totalElements;
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
