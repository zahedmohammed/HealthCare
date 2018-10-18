package com.fxlabs.issues.services.exceptions;

public class FxException extends RuntimeException {
    static final long serialVersionUID = 1L;

    public FxException() {
        super("User not authorized to the resource.");
    }

    public FxException(String msg) {
        super(msg);
    }

}
