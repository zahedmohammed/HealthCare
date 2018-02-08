package com.fxlabs.fxt.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Intesar Shannan Mohammed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<D> implements Serializable {
    private String requestId = "None";
    private Date requestTime = new Date();
    private boolean errors = false;
    private List<Message> messages = new ArrayList<>();
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

    public Response withErrors(boolean errors) {
        this.setErrors(errors);
        return this;
    }

    public Response withMessage(Message message) {
        this.getMessages().add(message);
        return this;
    }

    public Response withMessages(List<Message> message) {
        this.setMessages(message);
        return this;
    }


}

