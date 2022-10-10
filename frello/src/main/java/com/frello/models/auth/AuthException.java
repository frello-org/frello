package com.frello.models.auth;

public class AuthException extends Exception {
    public final Throwable cause;

    public AuthException(String message) {
        super("AuthorizationException: " + message);
        this.cause = null;
    }

    public AuthException(Throwable cause) {
        super("AuthorizationException: " + cause.toString());
        this.cause = cause;
    }
}
