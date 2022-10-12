package com.frello.services.common;

import lombok.Getter;

public class HttpException extends Exception {
    @Getter
    private int status;

    @Getter
    private String userMessage;

    @Getter
    private Throwable cause;

    public HttpException(int status, String userMessage) {
        this(status, userMessage, null);
    }

    public HttpException(int status, String userMessage, Throwable cause) {
        super(String.format("HttpException (%d): %s, caused by: %s", status, userMessage, cause));

        this.status = status;
        this.userMessage = userMessage;
        this.cause = cause;
    }
}
