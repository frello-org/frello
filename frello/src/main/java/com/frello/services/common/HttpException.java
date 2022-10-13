package com.frello.services.common;

import com.frello.lib.exceptions.ConflictException;
import com.frello.lib.exceptions.NotFoundException;
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

    public HttpException(ConflictException conflictEx) {
        this(400, conflictEx.getMessage());
    }

    public HttpException(NotFoundException notFoundEx) {
        this(404, notFoundEx.getMessage());
    }
}
