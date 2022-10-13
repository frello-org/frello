package com.frello.services.common;

import lombok.Getter;

public class HttpException extends Exception {
    @Getter
    private final int status;

    @Getter
    private final String userMessage;

    @Getter
    private final Throwable cause;

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
